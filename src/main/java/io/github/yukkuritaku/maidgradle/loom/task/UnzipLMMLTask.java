package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftVersionMeta;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public abstract class UnzipLMMLTask extends Copy {

    @Input
    public abstract Property<String> getMinecraftVersion();

    public UnzipLMMLTask(){
        setGroup(MaidConstants.MAID_GRADLE);
    }

    @TaskAction
    public void run(){
        MaidGradleExtension maidGradleExtension = MaidGradleExtension.get(getProject());
        LoomGradleExtension loomGradleExtension = LoomGradleExtension.get(getProject());
        MinecraftVersionMeta meta = loomGradleExtension.getMinecraftProvider().getVersionInfo();
        getMinecraftVersion().set(meta.id());
        getMinecraftVersion().finalizeValue();
        from(maidGradleExtension.getLMMLOutputDirectory().file(MaidConstants.LittleMaidJarFileUrls.versionConvert(meta.id())));
        into(maidGradleExtension.getLMMLOutputDirectory());
    }


}
