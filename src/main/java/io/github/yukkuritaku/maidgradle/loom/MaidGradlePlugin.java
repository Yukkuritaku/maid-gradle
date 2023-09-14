package io.github.yukkuritaku.maidgradle.loom;

import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.task.BuildLittleMaidModelZipTask;
import io.github.yukkuritaku.maidgradle.loom.task.DownloadLittleMaidJarTask;
import net.fabricmc.loom.bootstrap.BootstrappedPlugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginAware;
import org.gradle.api.tasks.TaskContainer;

import java.io.IOException;
import java.util.Objects;

public class MaidGradlePlugin implements BootstrappedPlugin {

    public static final String MAID_GRADLE_VERSION = Objects.requireNonNullElse(MaidGradlePlugin.class.getPackage().getImplementationVersion(), "0.0.0+unknown");

    @Override
    public void apply(PluginAware pluginAware) {
        if (pluginAware instanceof Project project) {
            project.getLogger().lifecycle("Maid Gradle: {}", MAID_GRADLE_VERSION);
            final MaidGradleExtension maidGradleExtension = project.getExtensions().create("maidgradle", MaidGradleExtension.class, project);

            final TaskContainer tasks = project.getTasks();
            tasks.register("buildLittleMaidModelZip", BuildLittleMaidModelZipTask.class, task -> {
                task.dependsOn(tasks.named("jar"));
                task.setDescription("Generate Zipped LittleMaid Model.");
            });
            var downloadLittleMaidJars = tasks.register("downloadLittleMaidJars", DownloadLittleMaidJarTask.class, task -> {
                task.setDescription("Download LittleMaid Jar from dropbox.");
            });
            downloadLittleMaidJars.configure(downloadLittleMaidJarTask -> {
                downloadLittleMaidJarTask.getMinecraftVersion().set(maidGradleExtension.getMinecraftVersion());
                downloadLittleMaidJarTask.getLittleMaidModelLoaderVersion().set(maidGradleExtension.getLittleMaidModelLoaderVersion());
                downloadLittleMaidJarTask.getLittleMaidReBirthVersion().set(maidGradleExtension.getLittleMaidReBirthVersion());
            });
            try {
                downloadLittleMaidJars.get().downloadJars();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            project.getRepositories().add(project.getRepositories().flatDir(flatDirectoryArtifactRepository -> {
                        flatDirectoryArtifactRepository.dir(
                                "build/" + maidGradleExtension.getLMMLOutputDirectory().get().getAsFile().getName()
                        );
                        flatDirectoryArtifactRepository.dir(
                                "build/" + maidGradleExtension.getLMRBOutputDirectory().get().getAsFile().getName()
                        );
                    }
            ));


        }
    }
}
