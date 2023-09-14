package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.util.gradle.SourceSetHelper;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.*;
import org.gradle.api.tasks.bundling.Zip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;

public abstract class BuildLittleMaidModelZipTask extends Zip {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildLittleMaidModelZipTask.class);

    @OutputDirectory
    public abstract RegularFileProperty getLittleMaidModelOutputDir();

    @Input
    public SourceSetOutput getSourceSetOutputDir(){
        return SourceSetHelper.getMainSourceSet(getProject()).getOutput();
    }

    @Inject
    public BuildLittleMaidModelZipTask() {
        super();
        setGroup(MaidConstants.MAID_GRADLE);
        File littleMaidBuildDir = new File(getProject().getLayout().getBuildDirectory().get().getAsFile(), "littlemaidmodel-output");
        getLittleMaidModelOutputDir().set(littleMaidBuildDir);
        LOGGER.info("LittleMaidModel Output dir: {}", littleMaidBuildDir);
    }

    @TaskAction
    public void run() {
        String baseName = getArchiveBaseName().get();
        getArchiveBaseName().set("littleMaidMob-" + baseName + "-" + getArchiveVersion().get());
        from(getSourceSetOutputDir());
        String[] outputDir = getSourceSetOutputDir().getClassesDirs().getAsPath().split("[/\\\\]");
        LOGGER.info("Project sourceSet: {}(custom)", outputDir[outputDir.length - 1]);
    }


}
