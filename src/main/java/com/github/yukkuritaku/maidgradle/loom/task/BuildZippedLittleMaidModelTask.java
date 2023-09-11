package com.github.yukkuritaku.maidgradle.loom.task;

import com.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.bundling.Zip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class BuildZippedLittleMaidModelTask extends Zip {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildZippedLittleMaidModelTask.class);

    @InputDirectory
    public abstract SourceSetOutput getSourceSetOutputDir();

    @OutputDirectory
    public abstract RegularFileProperty getLittleMaidOutputDir();

    public BuildZippedLittleMaidModelTask(){
        setGroup(MaidConstants.MAID_GRADLE);
        File littleMaidBuildDir = new File(getProject().getLayout().getBuildDirectory().get().getAsFile(), "littlemaidmodel-generated");
        getLittleMaidOutputDir().set(littleMaidBuildDir);
        LOGGER.info("LittleMaidModel Output dir: {}", littleMaidBuildDir);
        if (getSourceSetOutputDir() == null){
            throw new RuntimeException("getSourceSetOutputDir is null! must be set manually. (sourceSets.main.output)");
        }
    }

    @TaskAction
    public void buildLittleMaidModel(){
        String archiveBaseName = getArchiveBaseName().get();
        String version = getArchiveVersion().get();
        getArchiveBaseName().set("littleMaidMob-" + archiveBaseName + "-" + version);
        from(getSourceSetOutputDir());
        LOGGER.info("Zip build successfully done!");
        LOGGER.info("Output dir: {}", getLittleMaidOutputDir().get().getAsFile().getAbsolutePath());
    }
}
