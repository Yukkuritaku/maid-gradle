package io.github.yukkuritaku.maidgradle.loom.task;

import net.fabricmc.loom.util.gradle.SourceSetHelper;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.*;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class BuildLittleMaidModelTask extends AbstractMaidTask {

    @Input
    public abstract SourceSet getZipSourceSetDir();

    @Input
    public abstract Property<String> getOutputName();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @Inject
    public BuildLittleMaidModelTask() {
        super();
        getOutputName().convention("littleMaidMob-" + getProject().getName() + "-" + getProject().getVersion());
        getOutputDir().convention(getProject().getLayout().getBuildDirectory().dir("littlemaidmodel-build"));
    }

    private void addDirRecursively(final ZipOutputStream zos, File file) throws IOException {
        File[] listedFiles = file.listFiles();
        if (listedFiles != null) {
            for (var listedFile : listedFiles) {
                if (listedFile.isDirectory()) {
                    addDirRecursively(zos, file);
                    continue;
                }
                ZipEntry zipEntry = new ZipEntry(listedFile.getAbsolutePath());
                var attr = Files.readAttributes(listedFile.toPath(), BasicFileAttributes.class);
                zipEntry.setLastModifiedTime(attr.lastModifiedTime());
                zipEntry.setCreationTime(attr.creationTime());
                zipEntry.setLastAccessTime(attr.lastAccessTime());
                zipEntry.setTime(attr.lastModifiedTime().toMillis());
                zos.putNextEntry(zipEntry);
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(listedFile))) {
                    byte[] b = new byte[1024];
                    int count;
                    while ((count = bis.read(b)) > 0) {
                        zos.write(b, 0, count);
                    }
                    zos.closeEntry();
                }
            }
        }
    }

    private void zip(String outputName, SourceSetOutput sourceSetOutput) {
        try (ZipOutputStream zos = new ZipOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(getOutputDir().file(outputName).get().getAsFile())))) {
            //zos.putNextEntry(new ZipEntry(sourceSetOutput.getClassesDirs().getAsPath()));
            sourceSetOutput.getClassesDirs().getFiles().forEach(file -> {
                        try {
                            addDirRecursively(zos, file.getAbsoluteFile());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            if (sourceSetOutput.getResourcesDir() != null) {
                addDirRecursively(zos, sourceSetOutput.getResourcesDir());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TaskAction
    public void run() {
        zip(getOutputName().get(), getZipSourceSetDir() == null ?
                SourceSetHelper.getMainSourceSet(getProject()).getOutput() : getZipSourceSetDir().getOutput());
    }
}
