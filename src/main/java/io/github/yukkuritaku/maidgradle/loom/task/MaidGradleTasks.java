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
        getTasks().register("buildLittleMaidModel", BuildZippedLittleMaidModelTask.class, task -> {
            task.dependsOn(getTasks().named("remapJar"));
            task.setDescription("Generate Zipped LittleMaid Model.");
        });
    }
}
