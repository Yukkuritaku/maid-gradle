package io.github.yukkuritaku.maidgradle.loom;

import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.task.BuildLittleMaidModelZipTask;
import io.github.yukkuritaku.maidgradle.loom.task.DownloadLittleMaidJarTask;
import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.bootstrap.BootstrappedPlugin;
import net.fabricmc.loom.util.Checksum;
import net.fabricmc.loom.util.gradle.GradleUtils;
import net.fabricmc.loom.util.service.ScopedSharedServiceManager;
import net.fabricmc.loom.util.service.SharedServiceManager;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginAware;
import org.gradle.api.tasks.TaskContainer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

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
            project.getRepositories().add(project.getRepositories().flatDir(flatDirectoryArtifactRepository -> {
                        flatDirectoryArtifactRepository.dir(
                                "build/" + maidGradleExtension.getLMMLOutputDirectory().get().getAsFile().getName()
                        );
                        flatDirectoryArtifactRepository.dir(
                                "build/" + maidGradleExtension.getLMRBOutputDirectory().get().getAsFile().getName()
                        );
                    }
            ));
            afterEvaluationWithService(project, sharedServiceManager -> {
                project.getLogger().info(":setting up littlemaid dependencies");
                final LoomGradleExtension extension = LoomGradleExtension.get(project);
                final boolean previousRefreshDeps = extension.refreshDeps();
                if (getAndLock(project)) {
                    project.getLogger().lifecycle("Found existing cache lock file, rebuilding loom cache. This may have been caused by a failed or canceled build.");
                    extension.setRefreshDeps(true);
                }
                try {
                    downloadLittleMaidJars.get().downloadJars();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                extension.getDependencyManager().handleDependencies(project, sharedServiceManager);
                releaseLock(project);
                extension.setRefreshDeps(previousRefreshDeps);
            });
        }
    }
    private Path getLockFile(Project project) {
        final LoomGradleExtension extension = LoomGradleExtension.get(project);
        final Path cacheDirectory = extension.getFiles().getUserCache().toPath();
        final String pathHash = Checksum.projectHash(project);
        return cacheDirectory.resolve("." + pathHash + ".lock");
    }

    private boolean getAndLock(Project project) {
        final Path lock = getLockFile(project);

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

    private void releaseLock(Project project) {
        final Path lock = getLockFile(project);

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

    private void afterEvaluationWithService(Project project, Consumer<SharedServiceManager> consumer) {
        GradleUtils.afterSuccessfulEvaluation(project, () -> {
            try (var serviceManager = new ScopedSharedServiceManager()) {
                consumer.accept(serviceManager);
            }
        });
    }
}
