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
import java.util.stream.Stream;
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

    /**
     * Taken from <a href="https://qiita.com/ry-s/items/961e295b74edb39768d0">ry-s(R S)'s qiita blog</a>
     *
     * @param rootCount Name count
     * @param path      filePath
     * @param zos       ZipOutputStream
     * @throws IOException if an I/O error occurs when opening the directory and zipping files
     */
    private void zipDirectory(int rootCount, Path path, ZipOutputStream zos) throws IOException {
        try (Stream<Path> paths = Files.list(path)) {
            paths.forEach(p -> {
                try {
                    var pathName = p.subpath(rootCount, p.getNameCount());
                    if (Files.isDirectory(p)) {
                        zos.putNextEntry(new ZipEntry(pathName + "/"));
                        zipDirectory(rootCount, p, zos);
                    } else {
                        var zipEntry = new ZipEntry(pathName.toString());
                        var attr = Files.readAttributes(p, BasicFileAttributes.class);
                        zipEntry.setLastModifiedTime(attr.lastModifiedTime());
                        zipEntry.setCreationTime(attr.creationTime());
                        zipEntry.setLastAccessTime(attr.lastAccessTime());
                        zipEntry.setTime(attr.lastModifiedTime().toMillis());
                        zos.putNextEntry(zipEntry);
                        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(p.toFile()))) {
                            byte[] b = new byte[1024];
                            int count;
                            while ((count = bis.read(b)) > 0) {
                                zos.write(b, 0, count);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void zip(String outputName, SourceSetOutput sourceSetOutput) {
        try (ZipOutputStream zos = new ZipOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(getOutputDir().file(outputName).get().getAsFile())))) {
            sourceSetOutput.getFiles().forEach(file -> {
                        File[] lf = file.listFiles();
                        if (lf != null) {
                            for (File f : lf) {
                                if (f.exists()) {
                                    if (f.isDirectory()) {
                                        try {
                                            zipDirectory(f.toPath().getNameCount(), f.toPath(), zos);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    } else {
                                        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f))) {
                                            var zipEntry = new ZipEntry(f.toPath().getFileName().toString());
                                            var attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
                                            zipEntry.setLastModifiedTime(attr.lastModifiedTime());
                                            zipEntry.setCreationTime(attr.creationTime());
                                            zipEntry.setLastAccessTime(attr.lastAccessTime());
                                            zipEntry.setTime(attr.lastModifiedTime().toMillis());
                                            zos.putNextEntry(zipEntry);
                                            byte[] b = new byte[1024];
                                            int count;
                                            while ((count = bis.read(b)) > 0) {
                                                zos.write(b, 0, count);
                                            }
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                        }
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TaskAction
    public void run() {
        zip(getOutputName().get(), SourceSetHelper.getMainSourceSet(getProject()).getOutput());
    }
}
