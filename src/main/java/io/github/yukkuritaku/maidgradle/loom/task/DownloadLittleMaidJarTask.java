package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.util.download.DownloadExecutor;
import net.fabricmc.loom.util.download.GradleDownloadProgressListener;
import net.fabricmc.loom.util.gradle.ProgressGroup;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public abstract class DownloadLittleMaidJarTask extends AbstractMaidTask {

    public DownloadLittleMaidJarTask() {
        super();
        setGroup(MaidConstants.MAID_GRADLE);
    }

    @Input public abstract Property<String> getMinecraftVersion();
    @Input public abstract Property<String> getLittleMaidModelLoaderVersion();
    @Input public abstract Property<String> getLittleMaidReBirthVersion();

    @TaskAction
    public void downloadJars() throws IOException{
        getProject().getLogger().lifecycle(":Download LittleMaid Dependencies...");
        try (ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaidModelLoader");
             DownloadExecutor executor = new DownloadExecutor(2)
        ) {
            String minecraftVersion = getMinecraftVersion().get();
            getProject().getLogger().lifecycle("Current Minecraft version: {}", getMinecraftVersion().get());
            getProject().getLogger().lifecycle("Current LittleMaidModelLoader version: {}", getLittleMaidModelLoaderVersion().get());
            getMaidExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMMLDownloadUrl(minecraftVersion, getLittleMaidModelLoaderVersion().get()))
                    .progress(new GradleDownloadProgressListener("LittleMaidModelLoader", progressGroup::createProgressLogger))
                    .downloadPathAsync(getMaidExtension().getLMMLOutputDirectory().get().file(
                            "LMML-" + minecraftVersion + "-" + getMaidExtension().getLittleMaidModelLoaderVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath(), executor);
            getProject().getLogger().lifecycle("Current LittleMaidReBirth version: {}", getLittleMaidReBirthVersion().get());
            getMaidExtension()
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(minecraftVersion, getLittleMaidReBirthVersion().get()))
                    .progress(new GradleDownloadProgressListener("LittleMaidReBirth", progressGroup::createProgressLogger))
                    .downloadPathAsync(getMaidExtension().getLMRBOutputDirectory().get().file(
                            "LMRB-" + minecraftVersion + "-" + getMaidExtension().getLittleMaidReBirthVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath(), executor);
        }
        getProject().getLogger().lifecycle(":Done!");
    }
}
