package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.util.download.DownloadExecutor;
import net.fabricmc.loom.util.download.GradleDownloadProgressListener;
import net.fabricmc.loom.util.gradle.ProgressGroup;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.IOException;

public abstract class DownloadLittleMaidJarTask extends AbstractMaidTask{

    @Input
    public abstract Property<Integer> getDownloadThreads();

    @Inject
    public DownloadLittleMaidJarTask(){
        super();
        getDownloadThreads().convention(Math.min(Runtime.getRuntime().availableProcessors(), 10));
    }

    @TaskAction
    public void downloadJars(){
        getProject().getLogger().lifecycle("Download LittleMaid Dependencies...");
        String versionInfo = getMaidExtension().getMinecraftVersion().get();
        try(ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaidModelLoader");
            DownloadExecutor executor = new DownloadExecutor(getDownloadThreads().get())
        ) {
            getLoomExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMMLDownloadUrl(versionInfo, getMaidExtension()))
                    .progress(new GradleDownloadProgressListener("LittleMaidModelLoader", progressGroup::createProgressLogger))
                    .downloadPathAsync(getMaidExtension().getLMMLOutputDirectory().get().file(
                            "LMML-" + versionInfo + "-" + getMaidExtension().getLittleMaidModelLoaderVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath(), executor);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try(ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaidReBirth");
            DownloadExecutor executor = new DownloadExecutor(getDownloadThreads().get())) {
            getLoomExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(versionInfo, getMaidExtension()))
                    .progress(new GradleDownloadProgressListener("LittleMaidReBirth", progressGroup::createProgressLogger))
                    .downloadPathAsync(getMaidExtension().getLMRBOutputDirectory().get().file(
                            "LMRB-" + versionInfo + "-" + getMaidExtension().getLittleMaidReBirthVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath(), executor);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
