package io.github.yukkuritaku.maidgradle.loom.task;

import de.undercouch.gradle.tasks.download.Download;
import io.github.yukkuritaku.maidgradle.loom.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftVersionMeta;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class DownloadLMRBTask extends Download {

    @Input
    public abstract Property<String> getMinecraftVersion();

    @Inject
    public DownloadLMRBTask() throws IOException {
        super();
        setGroup(MaidConstants.MAID_GRADLE);
        MinecraftVersionMeta versionInfo = LoomGradleExtension.get(getProject()).getMinecraftProvider().getVersionInfo();
        MaidGradleExtension extension = MaidGradleExtension.get(getProject());
        getMinecraftVersion().set(versionInfo.id());
        getMinecraftVersion().finalizeValue();
        //src(Objects.requireNonNull(MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(getMinecraftVersion().get()),
                //"Cannot find LittleMaidReBirth in minecraft version: " + getMinecraftVersion().get() + "!"));
        dest(new File(extension.getLMRBOutputDirectory().getAsFile().get(), getMinecraftVersion().get() + ".zip"));
        download();
    }
}
