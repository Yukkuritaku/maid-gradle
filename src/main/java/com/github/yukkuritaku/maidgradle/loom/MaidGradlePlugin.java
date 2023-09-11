package com.github.yukkuritaku.maidgradle.loom;

import com.github.yukkuritaku.maidgradle.loom.task.MaidGradleTasks;
import net.fabricmc.loom.bootstrap.BootstrappedPlugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginAware;

import java.util.List;

public class MaidGradlePlugin implements BootstrappedPlugin {

    private static final List<Class<? extends Runnable>> SETUP_JOBS = List.of(
            MaidGradleTasks.class
    );

    @Override
    public void apply(PluginAware pluginAware) {
        if (pluginAware instanceof Project project){
            SETUP_JOBS.forEach(clazz -> project.getObjects().newInstance(clazz).run());
        }
    }
}
