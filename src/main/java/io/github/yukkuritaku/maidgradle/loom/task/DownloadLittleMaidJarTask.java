package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.util.download.GradleDownloadProgressListener;
import net.fabricmc.loom.util.gradle.ProgressGroup;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public class DownloadLittleMaidJarTask extends AbstractMaidTask{

    public DownloadLittleMaidJarTask(){
        super();
    }

    @TaskAction
    public void downloadJars(){
        getProject().getLogger().lifecycle("Download LittleMaid Dependencies...");
        String versionInfo = getMaidExtension().getMinecraftVersion().get();
        try(ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaidModelLoader")) {
            getLoomExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMMLDownloadUrl(versionInfo, getMaidExtension()))
                    .progress(new GradleDownloadProgressListener("LittleMaidModelLoader", progressGroup::createProgressLogger))
                    .downloadPath(getMaidExtension().getLMMLOutputDirectory().get().file(
                            "LMML-" + versionInfo + "-" + getMaidExtension().getLittleMaidModelLoaderVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try(ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaidReBirth")) {
            getLoomExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(versionInfo, getMaidExtension()))
                    .progress(new GradleDownloadProgressListener("LittleMaidReBirth", progressGroup::createProgressLogger))
                    .downloadPath(getMaidExtension().getLMRBOutputDirectory().get().file(
                            "LMRB-" + versionInfo + "-" + getMaidExtension().getLittleMaidReBirthVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
