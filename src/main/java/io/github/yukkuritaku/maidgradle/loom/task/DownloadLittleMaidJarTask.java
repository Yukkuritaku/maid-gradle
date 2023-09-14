package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.util.download.DownloadExecutor;
import net.fabricmc.loom.util.download.GradleDownloadProgressListener;
import net.fabricmc.loom.util.gradle.ProgressGroup;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public abstract class DownloadLittleMaidJarTask extends AbstractMaidTask {

    public DownloadLittleMaidJarTask() {
        super();
    }

    @TaskAction
    public void downloadJars() throws IOException{
        getProject().getLogger().lifecycle("Download LittleMaid Dependencies...");
        try (ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaidModelLoader");
             DownloadExecutor executor = new DownloadExecutor(2)
        ) {
            String versionInfo = getMaidExtension().getMcVersion().get();
            getMaidExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMMLDownloadUrl(versionInfo, getMaidExtension()))
                    .progress(new GradleDownloadProgressListener("LittleMaidModelLoader", progressGroup::createProgressLogger))
                    .downloadPathAsync(getMaidExtension().getLMMLOutputDirectory().get().file(
                            "LMML-" + versionInfo + "-" + getMaidExtension().getLittleMaidModelLoaderVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath(), executor);
            getMaidExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(versionInfo, getMaidExtension()))
                    .progress(new GradleDownloadProgressListener("LittleMaidReBirth", progressGroup::createProgressLogger))
                    .downloadPathAsync(getMaidExtension().getLMRBOutputDirectory().get().file(
                            "LMRB-" + versionInfo + "-" + getMaidExtension().getLittleMaidReBirthVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath(), executor);
        }
        getProject().getLogger().lifecycle("Done!");
    }
}
