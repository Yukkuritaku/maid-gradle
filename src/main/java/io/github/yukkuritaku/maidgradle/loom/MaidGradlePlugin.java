package io.github.yukkuritaku.maidgradle.loom;

import io.github.yukkuritaku.maidgradle.loom.api.MaidGradleExtensionAPI;
import io.github.yukkuritaku.maidgradle.loom.configuration.MaidGradleConfigurations;
import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtensionImpl;
import io.github.yukkuritaku.maidgradle.loom.task.DownloadLittleMaidJarTask;
import io.github.yukkuritaku.maidgradle.loom.task.MaidGradleTasks;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.LoomGradlePlugin;
import net.fabricmc.loom.bootstrap.BootstrappedPlugin;
import net.fabricmc.loom.bootstrap.LoomGradlePluginBootstrap;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginAware;

import java.util.List;
import java.util.Objects;

public class MaidGradlePlugin implements BootstrappedPlugin {

    public static final String MAID_GRADLE_VERSION = Objects.requireNonNullElse(MaidGradlePlugin.class.getPackage().getImplementationVersion(), "0.0.0+unknown");
    private static final List<Class<? extends Runnable>> SETUP_JOBS = List.of(
            MaidGradleTasks.class,
            MaidGradleConfigurations.class
    );

    @Override
    public void apply(PluginAware pluginAware) {
        if (pluginAware instanceof Project project){
            project.getLogger().lifecycle("Maid Gradle: {}", MAID_GRADLE_VERSION);
            project.getExtensions().create(MaidGradleExtensionAPI.class, "maidgradle", MaidGradleExtensionImpl.class, project);
            project.getLogger().lifecycle("Adding repository with flatDir...");
            project.getRepositories().add(project.getRepositories().flatDir(flatDirectoryArtifactRepository -> {
                        flatDirectoryArtifactRepository.dir(
                                "build/" + MaidGradleExtension.get(project).getLMMLOutputDirectory().get().getAsFile().getName()
                        );

                        flatDirectoryArtifactRepository.dir(
                                "build/" + MaidGradleExtension.get(project).getLMRBOutputDirectory().get().getAsFile().getName()
                        );
                    }
            ));
            project.getLogger().lifecycle("Done!");
            project.afterEvaluate(p -> SETUP_JOBS.forEach(clazz -> project.getObjects().newInstance(clazz).run()));
        }
    }
}
