package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
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
import java.nio.file.Path;
import java.util.function.BiFunction;

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
            var minecraftVersion = getMinecraftVersion().get();
            var lmmlVersion = getLittleMaidModelLoaderVersion().get();
            getProject().getLogger().lifecycle("Current Minecraft version: {}", minecraftVersion);
            getProject().getLogger().lifecycle("Current LittleMaidModelLoader version: {}", lmmlVersion);

            //LittleMaidModelLoader
            downloadJar(MaidConstants.LittleMaidJarFileUrls.getLMMLDownloadUrl(minecraftVersion, lmmlVersion),
                    "LittleMaidModelLoader", progressGroup, executor, LittleMaidJarType.LMML);

            var lmmlDevDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMMLDevDownloadUrl(minecraftVersion, lmmlVersion);
            if (lmmlDevDownloadUrl != null){
                downloadJar(lmmlDevDownloadUrl, "LittleMaidModelLoader Dev", progressGroup, executor, LittleMaidJarType.LMML);
            }

            var lmmlSourcesDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMMLSourceDownloadUrl(minecraftVersion, lmmlVersion);
            if (lmmlSourcesDownloadUrl != null){
                downloadJar(lmmlSourcesDownloadUrl, "LittleMaidModelLoader Source", progressGroup, executor, LittleMaidJarType.LMML);
            }

            //LittleMaidReBirth
            getProject().getLogger().lifecycle("Current LittleMaidReBirth version: {}", getLittleMaidReBirthVersion().get());
            downloadJar(MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(minecraftVersion, getLittleMaidReBirthVersion().get()),
"LittleMaidReBirth", progressGroup, executor, LittleMaidJarType.LMRB);

            var lmrbDevDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMRBDevDownloadUrl(minecraftVersion, getLittleMaidReBirthVersion().get());
            if (lmrbDevDownloadUrl != null){
                downloadJar(lmrbDevDownloadUrl, "LittleMaidReBirth Dev", progressGroup, executor, LittleMaidJarType.LMRB);
            }

            var lmrbSourceDownloadUrl = MaidConstants.LittleMaidJarFileUrls.getLMRBSourceDownloadUrl(minecraftVersion, getLittleMaidReBirthVersion().get());
            if (lmrbSourceDownloadUrl != null){
                downloadJar(lmrbSourceDownloadUrl, "LittleMaidReBirth Source", progressGroup, executor, LittleMaidJarType.LMRB);
            }
        }catch (DownloadException e){
            getProject().getLogger().error("""
Download failed, maybe json download url outdated, please contact developer or create issue ("https://github.com/Yukkuritaku/maid-gradle/issues") to update json!
ダウンロードに失敗しました。jsonのダウンロードurlが古い可能性があります。開発者にコンタクトするかgithubのissue("https://github.com/Yukkuritaku/maid-gradle/issues")でアップデートを申請してください！
""", e);
        }
        getProject().getLogger().lifecycle(":Done!");
    }


    private void downloadJar(String downloadUrl,
                             String progressListenerName,
                             ProgressGroup progressGroup,
                             DownloadExecutor executor,
                             LittleMaidJarType jarType){
        getMaidExtension()
                .download(downloadUrl)
                .progress(new GradleDownloadProgressListener(progressListenerName, progressGroup::createProgressLogger))
                .downloadPathAsync(
                        jarType.toPath(Utils.lastStr("/", downloadUrl).replace("?dl=1", ""), getMaidExtension()),
                        executor);

    }

    private enum LittleMaidJarType{
        LMML((fileName, extension) -> extension.getLMMLOutputDirectory().get().file(fileName).getAsFile().toPath()),
        LMRB((fileName, extension) -> extension.getLMRBOutputDirectory().get().file(fileName).getAsFile().toPath());

        private final BiFunction<String, MaidGradleExtension, Path> toPathFunction;

        LittleMaidJarType(BiFunction<String, MaidGradleExtension, Path> toPathFunction){
            this.toPathFunction = toPathFunction;
        }

        public Path toPath(String fileName, MaidGradleExtension extension){
            return this.toPathFunction.apply(fileName, extension);
        }

    }
}
