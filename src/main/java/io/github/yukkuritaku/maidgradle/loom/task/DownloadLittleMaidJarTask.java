package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.MaidGradlePlugin;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import io.github.yukkuritaku.maidgradle.loom.util.Utils;
import net.fabricmc.loom.util.download.DownloadException;
import net.fabricmc.loom.util.download.DownloadExecutor;
import net.fabricmc.loom.util.download.GradleDownloadProgressListener;
import net.fabricmc.loom.util.gradle.ProgressGroup;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.IOException;

public abstract class DownloadLittleMaidJarTask extends AbstractMaidTask {

    @Inject
    public DownloadLittleMaidJarTask() {
        super();
    }

    @Input public abstract Property<String> getMinecraftVersion();
    @Input public abstract Property<String> getLittleMaidModelLoaderVersion();
    @Input public abstract Property<String> getLittleMaidReBirthVersion();
    @Input public abstract Property<Integer> getDownloadThreads();

    @TaskAction
    public void downloadJars() throws IOException{
        getProject().getLogger().lifecycle(":Download LittleMaid Dependencies...");
        try (ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaidModelLoader");
             DownloadExecutor executor = new DownloadExecutor(getDownloadThreads().get())
        ) {
            String minecraftVersion = getMinecraftVersion().get();
            String lmmlVersion = getLittleMaidModelLoaderVersion().get();
            getProject().getLogger().lifecycle("Current Minecraft version: {}", minecraftVersion);
            getProject().getLogger().lifecycle("Current LittleMaidModelLoader version: {}", lmmlVersion);

            String lmmlDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMMLDownloadUrl(minecraftVersion, lmmlVersion);
            String lmmlFileName = Utils.lastStr("/", lmmlDownloadUrl).replace("?dl=1", "");
            getMaidExtension()
                    .download(lmmlDownloadUrl)
                    .progress(new GradleDownloadProgressListener("LittleMaidModelLoader", progressGroup::createProgressLogger))
                    .downloadPathAsync(getMaidExtension().getLMMLOutputDirectory().get().file(lmmlFileName).getAsFile().toPath(), executor);

            String lmmlDevDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMMLDevDownloadUrl(minecraftVersion, lmmlVersion);
            if (lmmlDevDownloadUrl != null) {
                String lmmlDevFileName = Utils.lastStr("/", lmmlDevDownloadUrl).replace("?dl=1", "");
                getMaidExtension()
                        .download(lmmlDevDownloadUrl)
                        .progress(new GradleDownloadProgressListener("LittleMaidModelLoader Dev", progressGroup::createProgressLogger))
                        .downloadPathAsync(getMaidExtension().getLMMLOutputDirectory().get().file(lmmlDevFileName).getAsFile().toPath(), executor);
            }

            String lmmlSourcesDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMMLSourceDownloadUrl(minecraftVersion, lmmlVersion);
            if (lmmlSourcesDownloadUrl != null){
                String lmmlSourceFileName = Utils.lastStr("/", lmmlSourcesDownloadUrl).replace("?dl=1", "");
                getMaidExtension()
                        .download(lmmlSourcesDownloadUrl)
                        .progress(new GradleDownloadProgressListener("LittleMaidModelLoader Source", progressGroup::createProgressLogger))
                        .downloadPathAsync(getMaidExtension().getLMMLOutputDirectory().get().file(lmmlSourceFileName).getAsFile().toPath(), executor);
            }
            getProject().getLogger().lifecycle("Current LittleMaidReBirth version: {}", getLittleMaidReBirthVersion().get());

            String lmrbVersion = getLittleMaidReBirthVersion().get();
            String lmrbDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(minecraftVersion, lmrbVersion);
            String lmrbFileName = Utils.lastStr("/", lmrbDownloadUrl).replace("?dl=1", "");
            getMaidExtension()
                    .download(lmrbDownloadUrl)
                    .progress(new GradleDownloadProgressListener("LittleMaidReBirth", progressGroup::createProgressLogger))
                    .downloadPathAsync(getMaidExtension().getLMRBOutputDirectory().get().file(lmrbFileName).getAsFile().toPath(), executor);

            String lmrbDevDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMRBDevDownloadUrl(minecraftVersion, lmrbVersion);
            if (lmrbDevDownloadUrl != null){
                String lmrbDevFileName = Utils.lastStr("/", lmrbDevDownloadUrl).replace("?dl=1", "");
                getMaidExtension()
                        .download(lmrbDevDownloadUrl)
                        .progress(new GradleDownloadProgressListener("LittleMaidReBirth Dev", progressGroup::createProgressLogger))
                        .downloadPathAsync(getMaidExtension().getLMRBOutputDirectory().get().file(lmrbDevFileName).getAsFile().toPath(), executor);
            }

            String lmrbSourceDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMRBSourceDownloadUrl(minecraftVersion, lmrbVersion);
            if (lmrbSourceDownloadUrl != null){
                String lmrbSourceFileName = Utils.lastStr("/", lmrbSourceDownloadUrl).replace("?dl=1", "");
                getMaidExtension()
                        .download(lmrbSourceDownloadUrl)
                        .progress(new GradleDownloadProgressListener("LittleMaidReBirth Source", progressGroup::createProgressLogger))
                        .downloadPathAsync(getMaidExtension().getLMRBOutputDirectory().get().file(lmrbSourceFileName).getAsFile().toPath(), executor);
            }
        }catch (DownloadException e){
            getProject().getLogger().error("""
Download failed, maybe json download url outdated, please contact developer or create issue ("https://github.com/Yukkuritaku/maid-gradle/issues") to update json!
ダウンロードに失敗しました。jsonのダウンロードurlが古い可能性があります。開発者にコンタクトするかgithubのissue("https://github.com/Yukkuritaku/maid-gradle/issues")でアップデートを申請してください！
""", e);
        }
        getProject().getLogger().lifecycle(":Done!");
    }
}
