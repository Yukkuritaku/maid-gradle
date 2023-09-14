package io.github.yukkuritaku.maidgradle.loom;

import io.github.yukkuritaku.maidgradle.loom.api.MaidGradleExtensionAPI;
import io.github.yukkuritaku.maidgradle.loom.configuration.MaidGradleConfigurations;
import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtensionImpl;
import io.github.yukkuritaku.maidgradle.loom.task.MaidGradleTasks;
import net.fabricmc.loom.bootstrap.BootstrappedPlugin;
import net.fabricmc.loom.bootstrap.LoomGradlePluginBootstrap;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginAware;

import java.util.List;

public class MaidGradlePlugin implements BootstrappedPlugin {

    private static final List<Class<? extends Runnable>> SETUP_JOBS = List.of(
            MaidGradleTasks.class,
            MaidGradleConfigurations.class
    );

    @Override
    public void apply(PluginAware pluginAware) {
        pluginAware.getPlugins().getPlugin(LoomGradlePluginBootstrap.class).apply(pluginAware);
        if (pluginAware instanceof Project project){

            if (project.getExtensions().findByName("loom") == null){
                project.getLogger().error("This plugin is required to be put fabric-loom plugin below!");
            }
            project.getExtensions().create(MaidGradleExtensionAPI.class, "maidgradle", MaidGradleExtensionImpl.class, project);
            project.afterEvaluate(p -> {
                SETUP_JOBS.forEach(clazz -> project.getObjects().newInstance(clazz).run());
            });
        }
    }
}
