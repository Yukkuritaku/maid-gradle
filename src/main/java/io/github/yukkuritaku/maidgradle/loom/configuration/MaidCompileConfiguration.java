package io.github.yukkuritaku.maidgradle.loom.configuration;

import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.api.InterfaceInjectionExtensionAPI;
import net.fabricmc.loom.build.mixin.GroovyApInvoker;
import net.fabricmc.loom.build.mixin.JavaApInvoker;
import net.fabricmc.loom.build.mixin.KaptApInvoker;
import net.fabricmc.loom.build.mixin.ScalaApInvoker;
import net.fabricmc.loom.configuration.*;
import net.fabricmc.loom.configuration.accesswidener.AccessWidenerJarProcessor;
import net.fabricmc.loom.configuration.ifaceinject.InterfaceInjectionProcessor;
import net.fabricmc.loom.configuration.processors.MinecraftJarProcessorManager;
import net.fabricmc.loom.configuration.processors.ModJavadocProcessor;
import net.fabricmc.loom.configuration.providers.mappings.MappingConfiguration;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftJarConfiguration;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftProvider;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftSourceSets;
import net.fabricmc.loom.configuration.providers.minecraft.mapped.AbstractMappedMinecraftProvider;
import net.fabricmc.loom.configuration.providers.minecraft.mapped.IntermediaryMinecraftProvider;
import net.fabricmc.loom.configuration.providers.minecraft.mapped.NamedMinecraftProvider;
import net.fabricmc.loom.extension.MixinExtension;
import net.fabricmc.loom.util.Checksum;
import net.fabricmc.loom.util.Constants;
import net.fabricmc.loom.util.ExceptionUtil;
import net.fabricmc.loom.util.gradle.GradleUtils;
import net.fabricmc.loom.util.gradle.SourceSetHelper;
import net.fabricmc.loom.util.service.ScopedSharedServiceManager;
import net.fabricmc.loom.util.service.SharedServiceManager;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.AbstractCopyTask;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import sun.misc.Unsafe;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public abstract class MaidCompileConfiguration implements Runnable {

    @Inject
    protected abstract Project getProject();

    @Inject
    protected abstract TaskContainer getTasks();

    @Override
    public void run() {
        LoomGradleExtension extension = LoomGradleExtension.get(getProject());

        getTasks().named(JavaPlugin.JAVADOC_TASK_NAME, Javadoc.class).configure(javadoc -> {
            final SourceSet main = SourceSetHelper.getMainSourceSet(getProject());
            javadoc.setClasspath(main.getOutput().plus(main.getCompileClasspath()));
        });

        afterEvaluationWithService((serviceManager) -> {
            final ConfigContext configContext = new ConfigContextImpl(getProject(), serviceManager, extension);

            MinecraftSourceSets.get(getProject()).afterEvaluate(getProject());

            final boolean previousRefreshDeps = extension.refreshDeps();

            if (getAndLock()) {
                getProject().getLogger().lifecycle("Found existing cache lock file, rebuilding loom cache. This may have been caused by a failed or canceled build.");
                extension.setRefreshDeps(true);
            }

            try {
                setupMinecraft(configContext);
            } catch (Exception e) {
                throw ExceptionUtil.createDescriptiveWrapper(RuntimeException::new, "Failed to setup Minecraft", e);
            }

            /*LoomDependencyManager dependencyManager = new LoomDependencyManager();
            extension.setDependencyManager(dependencyManager);
            dependencyManager.handleDependencies(getProject(), serviceManager);*/

            releaseLock();
            extension.setRefreshDeps(previousRefreshDeps);

            MixinExtension mixin = LoomGradleExtension.get(getProject()).getMixin();

            if (mixin.getUseLegacyMixinAp().get()) {
                setupMixinAp(mixin);
            }

            configureDecompileTasks(configContext);
        });

        finalizedBy("idea", "genIdeaWorkspace");
        finalizedBy("eclipse", "genEclipseRuns");
        finalizedBy("cleanEclipse", "cleanEclipseRuns");

        // Add the "dev" jar to the "namedElements" configuration
        getProject().artifacts(artifactHandler -> artifactHandler.add(Constants.Configurations.NAMED_ELEMENTS, getTasks().named("jar")));

        // Ensure that the encoding is set to UTF-8, no matter what the system default is
        // this fixes some edge cases with special characters not displaying correctly
        // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
        getTasks().withType(AbstractCopyTask.class).configureEach(abstractCopyTask -> abstractCopyTask.setFilteringCharset(StandardCharsets.UTF_8.name()));
        getTasks().withType(JavaCompile.class).configureEach(javaCompile -> javaCompile.getOptions().setEncoding(StandardCharsets.UTF_8.name()));

        if (getProject().getPluginManager().hasPlugin("org.jetbrains.kotlin.kapt")) {
            // If loom is applied after kapt, then kapt will use the AP arguments too early for loom to pass the arguments we need for mixin.
            throw new IllegalArgumentException("fabric-loom must be applied BEFORE kapt in the plugins { } block.");
        }
    }

    // This is not thread safe across getProject()s synchronize it here just to be sure, might be possible to move this further down, but for now this will do.
    private synchronized void setupMinecraft(ConfigContext configContext) throws Exception {
        final Project project = configContext.project();
        final LoomGradleExtension extension = configContext.extension();
        final MinecraftJarConfiguration jarConfiguration = extension.getMinecraftJarConfiguration().get();

        // Provide the vanilla mc jars -- TODO share across getProject()s.
        final MinecraftProvider minecraftProvider = jarConfiguration.getMinecraftProviderFunction().apply(configContext);
        extension.setMinecraftProvider(minecraftProvider);
        minecraftProvider.provide();

        final DependencyInfo mappingsDep = DependencyInfo.create(getProject(), Constants.Configurations.MAPPINGS);
        final MappingConfiguration mappingConfiguration = MappingConfiguration.create(getProject(), configContext.serviceManager(), mappingsDep, minecraftProvider);
        extension.setMappingConfiguration(mappingConfiguration);
        mappingConfiguration.applyToProject(getProject(), mappingsDep);

        // Provide the remapped mc jars
        final IntermediaryMinecraftProvider<?> intermediaryMinecraftProvider = jarConfiguration.getIntermediaryMinecraftProviderBiFunction().apply(project, minecraftProvider);
        NamedMinecraftProvider<?> namedMinecraftProvider = jarConfiguration.getNamedMinecraftProviderBiFunction().apply(project, minecraftProvider);

        registerGameProcessors(configContext);
        MinecraftJarProcessorManager minecraftJarProcessorManager = MinecraftJarProcessorManager.create(getProject());

        if (minecraftJarProcessorManager != null) {
            // Wrap the named MC provider for one that will provide the processed jars
            namedMinecraftProvider = jarConfiguration.getProcessedNamedMinecraftProviderBiFunction().apply(namedMinecraftProvider, minecraftJarProcessorManager);
        }

        final var provideContext = new AbstractMappedMinecraftProvider.ProvideContext(true, extension.refreshDeps(), configContext);

        extension.setIntermediaryMinecraftProvider(intermediaryMinecraftProvider);
        intermediaryMinecraftProvider.provide(provideContext);

        extension.setNamedMinecraftProvider(namedMinecraftProvider);
        namedMinecraftProvider.provide(provideContext);
    }

    private void registerGameProcessors(ConfigContext configContext) {
        final LoomGradleExtension extension = configContext.extension();

        final boolean enableTransitiveAccessWideners = extension.getEnableTransitiveAccessWideners().get();
        extension.addMinecraftJarProcessor(AccessWidenerJarProcessor.class, "fabric-loom:access-widener", enableTransitiveAccessWideners, extension.getAccessWidenerPath());

        if (extension.getEnableModProvidedJavadoc().get()) {
            extension.addMinecraftJarProcessor(ModJavadocProcessor.class, "fabric-loom:mod-javadoc");
        }

        final InterfaceInjectionExtensionAPI interfaceInjection = extension.getInterfaceInjection();

        if (interfaceInjection.isEnabled()) {
            extension.addMinecraftJarProcessor(InterfaceInjectionProcessor.class, "fabric-loom:interface-inject", interfaceInjection.getEnableDependencyInterfaceInjection().get());
        }
    }

    private void setupMixinAp(MixinExtension mixin) {
        mixin.init();

        // Disable some things used by log4j via the mixin AP that prevent it from being garbage collected
        System.setProperty("log4j2.disable.jmx", "true");
        System.setProperty("log4j.shutdownHookEnabled", "false");
        System.setProperty("log4j.skipJansi", "true");

        getProject().getLogger().info("Configuring compiler arguments for Java");

        new JavaApInvoker(getProject()).configureMixin();

        if (getProject().getPluginManager().hasPlugin("scala")) {
            getProject().getLogger().info("Configuring compiler arguments for Scala");
            new ScalaApInvoker(getProject()).configureMixin();
        }

        if (getProject().getPluginManager().hasPlugin("org.jetbrains.kotlin.kapt")) {
            getProject().getLogger().info("Configuring compiler arguments for Kapt plugin");
            new KaptApInvoker(getProject()).configureMixin();
        }

        if (getProject().getPluginManager().hasPlugin("groovy")) {
            getProject().getLogger().info("Configuring compiler arguments for Groovy");
            new GroovyApInvoker(getProject()).configureMixin();
        }
    }

    private void configureDecompileTasks(ConfigContext configContext) {
        final LoomGradleExtension extension = configContext.extension();

        extension.getMinecraftJarConfiguration().get().getDecompileConfigurationBiFunction()
                .apply(configContext, extension.getNamedMinecraftProvider()).afterEvaluation();
    }

    private Path getLockFile() {
        final LoomGradleExtension extension = LoomGradleExtension.get(getProject());
        final Path cacheDirectory = extension.getFiles().getUserCache().toPath();
        final String pathHash = Checksum.projectHash(getProject());
        return cacheDirectory.resolve("." + pathHash + ".lock");
    }

    private boolean getAndLock() {
        final Path lock = getLockFile();

        if (Files.exists(lock)) {
            return true;
        }

        try {
            Files.createFile(lock);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to acquire getProject() configuration lock", e);
        }

        return false;
    }

    private void releaseLock() {
        final Path lock = getLockFile();

        if (!Files.exists(lock)) {
            return;
        }

        try {
            Files.delete(lock);
        } catch (IOException e1) {
            try {
                // If we failed to delete the lock file, moving it before trying to delete it may help.
                final Path del = lock.resolveSibling(lock.getFileName() + ".del");
                Files.move(lock, del);
                Files.delete(del);
            } catch (IOException e2) {
                var exception = new UncheckedIOException("Failed to release getProject() configuration lock", e2);
                exception.addSuppressed(e1);
                throw exception;
            }
        }
    }

    private void finalizedBy(String a, String b) {
        getTasks().named(a).configure(task -> task.finalizedBy(getTasks().named(b)));
    }

    private void afterEvaluationWithService(Consumer<SharedServiceManager> consumer) {
        GradleUtils.afterSuccessfulEvaluation(getProject(), () -> {
            try (var serviceManager = new ScopedSharedServiceManager()) {
                consumer.accept(serviceManager);
            }
        });
    }
}
