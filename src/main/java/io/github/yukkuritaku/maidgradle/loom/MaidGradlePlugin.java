package io.github.yukkuritaku.maidgradle.loom;

import io.github.yukkuritaku.maidgradle.loom.configuration.MaidGradleConfigurations;
import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.task.MaidGradleTasks;
import net.fabricmc.loom.bootstrap.BootstrappedPlugin;
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
        if (pluginAware instanceof Project project) {
            project.getLogger().lifecycle("Maid Gradle: {}", MAID_GRADLE_VERSION);
            final MaidGradleExtension maidGradleExtension = project.getExtensions().create("maidgradle", MaidGradleExtension.class, project);
            project.getRepositories().add(project.getRepositories().flatDir(flatDirectoryArtifactRepository -> {
                        flatDirectoryArtifactRepository.dir(
                                "build/" + maidGradleExtension.getLMMLOutputDirectory().get().getAsFile().getName()
                        );

                        flatDirectoryArtifactRepository.dir(
                                "build/" + maidGradleExtension.getLMRBOutputDirectory().get().getAsFile().getName()
                        );
                    }
            ));
            SETUP_JOBS.forEach(clazz -> project.getObjects().newInstance(clazz).run());


        }
    }
}
