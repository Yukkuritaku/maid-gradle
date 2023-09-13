package io.github.yukkuritaku.maidgradle.loom.extension;

import io.github.yukkuritaku.maidgradle.loom.MaidGradleExtension;
import org.gradle.api.Project;

public class MaidGradleExtensionImpl extends MaidGradleExtensionApiImpl implements MaidGradleExtension {
    public MaidGradleExtensionImpl(Project project) {
        super(project);
    }
}
