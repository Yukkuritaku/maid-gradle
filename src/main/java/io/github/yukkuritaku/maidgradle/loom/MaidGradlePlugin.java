package io.github.yukkuritaku.maidgradle.loom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.task.BuildLittleMaidModelTask;
import io.github.yukkuritaku.maidgradle.loom.task.DownloadLittleMaidJarTask;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import io.github.yukkuritaku.maidgradle.loom.util.Utils;
import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.api.RemapConfigurationSettings;
import net.fabricmc.loom.bootstrap.BootstrappedPlugin;
import net.fabricmc.loom.configuration.LoomDependencyManager;
import net.fabricmc.loom.util.Checksum;
import net.fabricmc.loom.util.download.DownloadException;
import net.fabricmc.loom.util.gradle.GradleUtils;
import net.fabricmc.loom.util.gradle.SourceSetHelper;
import net.fabricmc.loom.util.service.ScopedSharedServiceManager;
import net.fabricmc.loom.util.service.SharedServiceManager;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.PluginAware;
import org.gradle.api.tasks.TaskContainer;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class MaidGradlePlugin implements BootstrappedPlugin {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public static final String MAID_GRADLE_VERSION = Objects.requireNonNullElse(MaidGradlePlugin.class.getPackage().getImplementationVersion(), "0.0.0+unknown");

    @Override
    public void apply(PluginAware pluginAware) {
        if (pluginAware instanceof Project project) {
            project.getLogger().lifecycle("Maid Gradle: {}", MAID_GRADLE_VERSION);
            final MaidGradleExtension maidGradleExtension = project.getExtensions().create("maidgradle", MaidGradleExtension.class, project);
            final TaskContainer tasks = project.getTasks();
            //Tasks
            tasks.register("buildLittleMaidModel", BuildLittleMaidModelTask.class, task -> {
                task.dependsOn(tasks.named("jar"));
                task.setDescription("Build LittleMaid Model.");
            });
            var downloadLittleMaidJars = tasks.register("downloadLittleMaidJars", DownloadLittleMaidJarTask.class, task -> {
                task.setDescription("Download LittleMaid Jar from dropbox. (This task is automatically runs in gradle configuration)");
            });
            downloadLittleMaidJars.configure(downloadLittleMaidJarTask -> {
                downloadLittleMaidJarTask.getMinecraftVersion().set(maidGradleExtension.getMinecraftVersion());
                downloadLittleMaidJarTask.getLittleMaidModelLoaderVersion().set(maidGradleExtension.getLittleMaidModelLoaderVersion());
                downloadLittleMaidJarTask.getLittleMaidReBirthVersion().set(maidGradleExtension.getLittleMaidReBirthVersion());
                downloadLittleMaidJarTask.getDownloadThreads().set(Math.min(Runtime.getRuntime().availableProcessors(), 6));
            });

            String projectDir = project.getLayout().getProjectDirectory().getAsFile().getAbsolutePath().replace("\\", "/") + "/";
            String lmmlOutputDir = maidGradleExtension.getLMMLOutputDirectory().get().getAsFile().getAbsolutePath().replace("\\", "/");
            String lmrbOutputDir = maidGradleExtension.getLMRBOutputDirectory().get().getAsFile().getAbsolutePath().replace("\\", "/");
            //githubからバージョンのデータをダウンロード
            try {
                project.getLogger().lifecycle("read littlemaid-modelloader-url.json from github");
                String lmmlJson = maidGradleExtension
                        .download("https://raw.githubusercontent.com/Yukkuritaku/maid-gradle/master/littlemaid-json-data/littlemaid-modelloader-url.json")
                        .downloadString();
                MaidConstants.LittleMaidJarFileUrls.setLmmlJarUrlMapping(GSON.fromJson(lmmlJson, new TypeToken<>() {
                }));
                project.getLogger().lifecycle("read littlemaid-rebirth-url.json from github");
                String lmrbJson = maidGradleExtension
                        .download("https://raw.githubusercontent.com/Yukkuritaku/maid-gradle/master/littlemaid-json-data/littlemaid-rebirth-url.json")
                        .downloadString();
                MaidConstants.LittleMaidJarFileUrls.setLmrbJarUrlMapping(GSON.fromJson(lmrbJson, new TypeToken<>() {
                }));
            } catch (DownloadException e) {
                throw new RuntimeException(e);
            }

            //ファイルがない場合に新しく作る
            if (!maidGradleExtension.getLMMLOutputDirectory().get().getAsFile().exists()) {
                maidGradleExtension.getLMMLOutputDirectory().get().getAsFile().mkdir();
            }
            if (!maidGradleExtension.getLMRBOutputDirectory().get().getAsFile().exists()) {
                maidGradleExtension.getLMRBOutputDirectory().get().getAsFile().mkdir();
            }

            afterEvaluationWithService(project, sharedServiceManager -> {
                final LoomGradleExtension extension = LoomGradleExtension.get(project);
                project.getLogger().lifecycle(":setting up littlemaid dependencies");
                //メイドさんのJarファイルをダウンロード
                try {
                    downloadLittleMaidJars.get().downloadJars();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //Register configurations
                project.getConfigurations().register(MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER, c -> {
                    c.setCanBeConsumed(false);
                    c.setCanBeResolved(true);
                });
                project.getConfigurations().register(MaidConstants.Configurations.LITTLE_MAID_REBIRTH, c -> {
                    c.setCanBeConsumed(false);
                    c.setCanBeResolved(true);
                });
                extendsFrom(project, SourceSetHelper.getMainSourceSet(project).getImplementationConfigurationName(), MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER);
                extendsFrom(project, SourceSetHelper.getMainSourceSet(project).getImplementationConfigurationName(), MaidConstants.Configurations.LITTLE_MAID_REBIRTH);
                //modApiと同じ
                extension.addRemapConfiguration(MaidConstants.Configurations.MOD_LITTLE_MAID_MODEL_LOADER, remapConfigurationSettings -> {
                    remapConfigurationSettings.getSourceSet().convention(SourceSetHelper.getMainSourceSet(project));
                    remapConfigurationSettings.getTargetConfigurationName().convention(MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER);
                    remapConfigurationSettings.getOnCompileClasspath().convention(true);
                    remapConfigurationSettings.getOnRuntimeClasspath().convention(true);
                    remapConfigurationSettings.getPublishingMode().convention(RemapConfigurationSettings.PublishingMode.COMPILE_AND_RUNTIME);
                });
                //modImplementationと同じ
                extension.addRemapConfiguration(MaidConstants.Configurations.MOD_LITTLE_MAID_REBIRTH, remapConfigurationSettings -> {
                    remapConfigurationSettings.getSourceSet().convention(SourceSetHelper.getMainSourceSet(project));
                    remapConfigurationSettings.getTargetConfigurationName().convention(MaidConstants.Configurations.LITTLE_MAID_REBIRTH);
                    remapConfigurationSettings.getOnCompileClasspath().convention(true);
                    remapConfigurationSettings.getOnRuntimeClasspath().convention(true);
                    remapConfigurationSettings.getPublishingMode().convention(RemapConfigurationSettings.PublishingMode.RUNTIME_ONLY);
                });
                final boolean previousRefreshDeps = extension.refreshDeps();
                if (getAndLock(project)) {
                    project.getLogger().lifecycle("Found existing cache lock file, rebuilding loom cache. This may have been caused by a failed or canceled build.");
                    extension.setRefreshDeps(true);
                }
                project.getRepositories().add(project.getRepositories().flatDir(flatDirectoryArtifactRepository -> {
                            flatDirectoryArtifactRepository.dir(lmmlOutputDir.replace(projectDir, ""));
                            flatDirectoryArtifactRepository.dir(lmrbOutputDir.replace(projectDir, ""));
                        }
                ));
                //devファイルはどうやってfabricに入れればいいのかわからん
                //今の所はremapされたjarをプロジェクトに入れるようにする
                try {
                    String lmmlFileName = Utils.lastStr("/", Objects.requireNonNull(MaidConstants.LittleMaidJarFileUrls
                                    .getLMMLDownloadUrl(maidGradleExtension.getMinecraftVersion().get(),
                                            maidGradleExtension.getLittleMaidModelLoaderVersion().get())))
                            .replace("?dl=1", "");
                    String lmrbFileName = Utils.lastStr("/", Objects.requireNonNull(MaidConstants.LittleMaidJarFileUrls
                                    .getLMRBDownloadUrl(maidGradleExtension.getMinecraftVersion().get(),
                                            maidGradleExtension.getLittleMaidReBirthVersion().get())))
                            .replace("?dl=1", "");
                    if (project.file(lmmlOutputDir + "/" + lmmlFileName).exists()) {
                        project.getDependencies().add(MaidConstants.Configurations.MOD_LITTLE_MAID_MODEL_LOADER, MaidConstants.Dependencies.getLittleMaidModelLoader(project));
                    }
                    if (project.file(lmrbOutputDir + "/" + lmrbFileName).exists()) {
                        project.getDependencies().add(MaidConstants.Configurations.MOD_LITTLE_MAID_REBIRTH, MaidConstants.Dependencies.getLittleMaidReBirth(project));
                    }
                } catch (Exception ignored) {
                }
                LoomDependencyManager dependencyManager = new LoomDependencyManager();
                extension.setDependencyManager(dependencyManager);
                dependencyManager.handleDependencies(project, sharedServiceManager);
                releaseLock(project);
                extension.setRefreshDeps(previousRefreshDeps);
                extension.getRuns().forEach(
                        runConfigSettings -> {
                            runConfigSettings.property("lmmd.dev.classes",
                                    SourceSetHelper.getMainSourceSet(project).getOutput().getClassesDirs().getAsPath());
                            runConfigSettings.property("lmmd.dev.resources",
                                    SourceSetHelper.getMainSourceSet(project).getOutput().getResourcesDir().getAbsolutePath());

                        }
                );
            });
        }
    }

    public void extendsFrom(Project project, String a, String b) {
        project.getConfigurations().getByName(a, configuration -> configuration.extendsFrom(project.getConfigurations().getByName(b)));
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
