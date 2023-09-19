package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;

public abstract class AbstractMaidTask extends DefaultTask {

    public AbstractMaidTask(){
        setGroup(MaidConstants.MAID_GRADLE);
    }

    @Internal
    protected MaidGradleExtension getMaidExtension() {
        return getProject().getExtensions().getByType(MaidGradleExtension.class);
    }
}
