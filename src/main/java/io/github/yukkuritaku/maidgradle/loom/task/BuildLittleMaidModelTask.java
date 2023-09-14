package io.github.yukkuritaku.maidgradle.loom.task;

import net.fabricmc.loom.util.gradle.SourceSetHelper;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.*;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class BuildLittleMaidModelTask extends AbstractMaidTask {

    @Input
    public abstract Property<String> getOutputName();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @Inject
    public BuildLittleMaidModelTask() {
        super();
        getOutputName().convention("littleMaidMob-" + getProject().getName() + "-" + getProject().getVersion() + ".zip");
        getOutputDir().convention(getProject().getLayout().getBuildDirectory().dir("littlemaidmodel-build"));
    }

    private String fileToRelativePath(File file, String baseDir) {
        return file.getAbsolutePath()
                .substring(baseDir.length() + 1);
    }

    private void addDirRecursively(String name, String baseDir, final ZipOutputStream zos, File file) throws IOException {
        File[] listedFiles = file.listFiles();
        if (listedFiles != null) {
            for (var listedFile : listedFiles) {
                getProject().getLogger().lifecycle(listedFile.getAbsolutePath());
                if (listedFile.isDirectory()) {
                    addDirRecursively(name, baseDir, zos, listedFile);
                    continue;
                }
                ZipEntry zipEntry = new ZipEntry(name + File.separatorChar +
                        fileToRelativePath(file, baseDir));
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
                            File[] listedFiles = file.listFiles();
                            if (listedFiles != null) {
                                String appendPkgStr = null;
                                for (var lf: listedFiles){
                                    if (lf.isDirectory()){
                                        appendPkgStr = lf.getName();
                                        break;
                                    }
                                }
                                getProject().getLogger().lifecycle(file.getAbsolutePath());
                                addDirRecursively(file.getName(),
                                        appendPkgStr != null ? file.getAbsolutePath() + File.separator + appendPkgStr : file.getAbsolutePath(),
                                        zos, file);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            if (sourceSetOutput.getResourcesDir() != null) {
                File[] listedFiles = sourceSetOutput.getResourcesDir().listFiles();
                if (listedFiles != null) {
                    String appendPkgStr = null;
                    for (var lf: listedFiles){
                        if (lf.isDirectory()){
                            appendPkgStr = lf.getName();
                            break;
                        }
                    }
                    getProject().getLogger().lifecycle(sourceSetOutput.getResourcesDir().getAbsolutePath());
                    addDirRecursively(sourceSetOutput.getResourcesDir().getName(),
                            appendPkgStr != null ? sourceSetOutput.getResourcesDir().getAbsolutePath() + File.separator + appendPkgStr : sourceSetOutput.getResourcesDir().getAbsolutePath(),
                            zos, sourceSetOutput.getResourcesDir());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TaskAction
    public void run() {
        zip(getOutputName().get(), SourceSetHelper.getMainSourceSet(getProject()).getOutput());
    }
}
