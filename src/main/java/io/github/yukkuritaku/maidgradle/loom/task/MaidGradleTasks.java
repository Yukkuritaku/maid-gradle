package io.github.yukkuritaku.maidgradle.loom.task;

import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

import javax.inject.Inject;

public abstract class MaidGradleTasks implements Runnable{

    @Inject
    protected abstract Project getProject();

    @Inject
    protected abstract TaskContainer getTasks();

    @Override
    public void run() {
        getTasks().register("buildLittleMaidModelZip", BuildLittleMaidModelZipTask.class, task -> {
            task.dependsOn(getTasks().named("jar"));
            task.setDescription("Generate Zipped LittleMaid Model.");
        });
        var jarTask = getTasks().register("downloadLittleMaidJars", DownloadLittleMaidJarTask.class, task -> {
            task.mustRunAfter(getTasks().named("downloadAssets"));
            task.setDescription("Download LittleMaid Jar from dropbox.");
        });
        jarTask.get().downloadJars();
    }
}
