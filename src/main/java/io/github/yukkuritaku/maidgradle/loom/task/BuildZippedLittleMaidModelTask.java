package io.github.yukkuritaku.maidgradle.loom.task;

import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.task.RemapJarTask;
import net.fabricmc.loom.util.ExceptionUtil;
import net.fabricmc.loom.util.ZipReprocessorUtil;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BuildZippedLittleMaidModelTask extends RemapJarTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildZippedLittleMaidModelTask.class);

    @OutputDirectory
    public abstract RegularFileProperty getLittleMaidOutputDir();

    public BuildZippedLittleMaidModelTask(){
        super();
        setGroup(MaidConstants.MAID_GRADLE);
        File littleMaidBuildDir = new File(getProject().getLayout().getBuildDirectory().get().getAsFile(), "littlemaidmodel-generated");
        getLittleMaidOutputDir().set(littleMaidBuildDir);
        LOGGER.info("LittleMaidModel Output dir: {}", littleMaidBuildDir);
    }

    @TaskAction
    public void buildLittleMaidModel(){
        run();
        submitWork(RenameAction.class, params -> params.getOutputFile().set(getLittleMaidOutputDir()));
    }

    public abstract static class RenameAction extends AbstractRemapAction<AbstractRemapParams> {

        protected final Path inputFile;
        protected final Path outputFile;

        @Inject
        public RenameAction(){
            inputFile = getParameters().getInputFile().getAsFile().get().toPath();
            outputFile = Path.of(getParameters().getOutputFile().getAsFile().get().toPath().toString().replace(".jar", ".zip"));
        }

        protected void rewriteToZip() throws IOException {
            final boolean isReproducibleFileOrder = getParameters().getArchiveReproducibleFileOrder().get();
            final boolean isPreserveFileTimestamps = getParameters().getArchivePreserveFileTimestamps().get();

            if (isReproducibleFileOrder || !isPreserveFileTimestamps) {
                ZipReprocessorUtil.reprocessZip(outputFile.toFile(), isReproducibleFileOrder, isPreserveFileTimestamps);
            }
        }

        @Override
        public void execute() {
            try {
                rewriteToZip();
            } catch (IOException e) {
                try {
                    Files.deleteIfExists(outputFile);
                } catch (IOException ex) {
                    LOGGER.error("Failed to delete output file", ex);
                }

                throw ExceptionUtil.createDescriptiveWrapper(RuntimeException::new, "Failed to remapping file to zip", e);
            }
        }
    }
}
