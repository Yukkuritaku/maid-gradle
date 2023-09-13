package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftVersionMeta;
import net.fabricmc.loom.util.download.DownloadException;
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
        MinecraftVersionMeta versionInfo = getLoomExtension().getMinecraftProvider().getVersionInfo();
        try(ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaid Jars")) {
            getLoomExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMMLDownloadUrl(versionInfo.id(), getMaidExtension()))
                    .progress(new GradleDownloadProgressListener("LittleMaidModelLoader", progressGroup::createProgressLogger))
                    .downloadPath(getMaidExtension().getLMMLOutputDirectory().get().getAsFile().toPath());
            getLoomExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(versionInfo.id(), getMaidExtension()))
                    .progress(new GradleDownloadProgressListener("LittleMaidReBirth", progressGroup::createProgressLogger))
                    .downloadPath(getMaidExtension().getLMMLOutputDirectory().get().getAsFile().toPath());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
