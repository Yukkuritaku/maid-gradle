package io.github.yukkuritaku.maidgradle.loom.configuration;

import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.configuration.CompileConfiguration;
import net.fabricmc.loom.configuration.ConfigContext;
import net.fabricmc.loom.configuration.ConfigContextImpl;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftSourceSets;
import net.fabricmc.loom.extension.MixinExtension;
import net.fabricmc.loom.util.Constants;
import net.fabricmc.loom.util.ExceptionUtil;
import net.fabricmc.loom.util.gradle.GradleUtils;
import net.fabricmc.loom.util.gradle.SourceSetHelper;
import net.fabricmc.loom.util.service.ScopedSharedServiceManager;
import net.fabricmc.loom.util.service.SharedServiceManager;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.AbstractCopyTask;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import sun.misc.Unsafe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public abstract class MaidCompileConfiguration extends CompileConfiguration {

    @Override
    public void run() {
        LoomGradleExtension extension = LoomGradleExtension.get(getProject());
        getTasks().named(JavaPlugin.JAVADOC_TASK_NAME, Javadoc.class).configure(javadoc -> {
            final SourceSet main = SourceSetHelper.getMainSourceSet(getProject());
            javadoc.setClasspath(main.getOutput().plus(main.getCompileClasspath()));
        });
        try {
            Class<?> compileConfigurationClass = Class.forName(CompileConfiguration.class.getSimpleName());
            Method setupMinecraft = compileConfigurationClass.getMethod("setupMinecraft", ConfigContext.class);
            Method getAndLock = compileConfigurationClass.getMethod("getAndLock");
            Method releaseLock = compileConfigurationClass.getMethod("releaseLock");
            Method setupMixinAp = compileConfigurationClass.getMethod("setupMixinAp", MixinExtension.class);
            Method configureDecompileTasks = compileConfigurationClass.getMethod("configureDecompileTasks", ConfigContext.class);
            afterEvaluationWithService(serviceManager -> {
                final ConfigContext configContext = new ConfigContextImpl(getProject(), serviceManager, extension);
                MinecraftSourceSets.get(getProject()).afterEvaluate(getProject());
                final boolean previousRefreshDeps = extension.refreshDeps();
                if ((Boolean) tryInvoke(getAndLock, compileConfigurationClass)) {
                    getProject().getLogger().lifecycle("Found existing cache lock file, rebuilding loom cache. This may have been caused by a failed or canceled build.");
                    extension.setRefreshDeps(true);
                }
                try {
                    setupMinecraft.invoke(compileConfigurationClass, configContext);
                } catch (Exception e) {
                    throw ExceptionUtil.createDescriptiveWrapper(RuntimeException::new, "Failed to setup Minecraft", e);
                }
                tryInvoke(releaseLock, compileConfigurationClass);

                MixinExtension mixin = LoomGradleExtension.get(getProject()).getMixin();

                if (mixin.getUseLegacyMixinAp().get()) {
                    tryInvoke(setupMixinAp, compileConfigurationClass, mixin);
                }
                tryInvoke(configureDecompileTasks, compileConfigurationClass, configContext);
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
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Object tryInvoke(Method method, Class<?> callingClass, Object... args) {
        try {
            return method.invoke(callingClass, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
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
