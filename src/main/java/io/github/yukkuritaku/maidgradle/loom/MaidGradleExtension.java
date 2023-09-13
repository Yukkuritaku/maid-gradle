package io.github.yukkuritaku.maidgradle.loom;

import io.github.yukkuritaku.maidgradle.loom.api.MaidGradleExtensionAPI;
import net.fabricmc.loom.LoomGradleExtension;
import org.gradle.api.Project;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface MaidGradleExtension extends MaidGradleExtensionAPI {
    static MaidGradleExtension get(Project project) {
        return (MaidGradleExtension) project.getExtensions().getByName("maidgradle");
    }
}
