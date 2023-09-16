package io.github.yukkuritaku.maidgradle.loom.task;

import net.fabricmc.loom.util.gradle.SourceSetHelper;
import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.utils.IOUtils;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.*;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;

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

    private void setMethod(File file, ZipArchiveEntry entry) throws IOException {
        CRC32 crc32 = new CRC32();
        if (file.isDirectory()) {
            BiPredicate<Path, BasicFileAttributes> predicate = (p, a) ->
                    a.isRegularFile();
            AtomicLong size = new AtomicLong();
            try (Stream<Path> stream = Files.find(file.toPath(), Integer.MAX_VALUE, predicate)) {
                stream.forEach(path -> {
                    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path.toFile()))) {
                        size.addAndGet(Files.size(path));
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = bis.read(buf)) > 0) {
                            crc32.update(buf, 0, len);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            entry.setMethod(ZipEntry.STORED);
            entry.setSize(size.get());
            entry.setCrc(crc32.getValue());
        } else {
            //pngの場合は無圧縮にする
            if (file.getName().endsWith(".png")) {
                entry.setMethod(ZipEntry.STORED);
                entry.setSize(Files.size(file.toPath()));
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                    byte[] buf = new byte[1024];
                    int len = 0;

                    while ((len = bis.read(buf)) > 0) {
                        crc32.update(buf, 0, len);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                entry.setCrc(crc32.getValue());
            }
        }
    }

    /**
     * Taken and modified from <a href="https://qiita.com/ry-s/items/961e295b74edb39768d0">ry-s(R S)'s qiita blog</a>
     *
     * @param rootCount Name count
     * @param path      filePath
     * @param zos       ZipOutputStream
     * @throws IOException if an I/O error occurs when opening the directory and zipping files
     */
    private void zipDirectory(int rootCount, Path path, ZipArchiveOutputStream zos) throws IOException {
        try (Stream<Path> paths = Files.list(path)) {
            paths.forEach(p -> {
                try {
                    var pathName = p.subpath(rootCount, p.getNameCount());
                    if (Files.isDirectory(p)) {
                        ZipArchiveEntry entry = new ZipArchiveEntry(pathName + "/");
                        setMethod(p.toFile(), entry);
                        zos.putArchiveEntry(entry);
                        zos.closeArchiveEntry();
                        zipDirectory(rootCount, p, zos);
                    } else {
                        var zipEntry = new ZipArchiveEntry(pathName.toString());
                        setMethod(p.toFile(), zipEntry);
                        zipEntry.addExtraField(ExtraFieldUtils.createExtraField(X000A_NTFS.HEADER_ID));
                        zos.putArchiveEntry(zipEntry);
                        IOUtils.copy(new FileInputStream(p.toFile()), zos);
                        zos.closeArchiveEntry();
                    }
                } catch (IOException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void zip(String outputName, SourceSetOutput sourceSetOutput) {
        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(getOutputDir().file(outputName).get().getAsFile())) {
            zos.setLevel(5);
            sourceSetOutput.getFiles().forEach(file -> {
                        if (file.exists()) {
                            if (file.isDirectory()) {
                                try {
                                    zipDirectory(file.toPath().getNameCount(), file.toPath(), zos);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                try {
                                    ZipArchiveEntry archiveEntry = new ZipArchiveEntry(file.toPath().getFileName().toString());
                                    setMethod(file, archiveEntry);
                                    archiveEntry.addExtraField(ExtraFieldUtils.createExtraField(X000A_NTFS.HEADER_ID));
                                    zos.putArchiveEntry(archiveEntry);
                                    IOUtils.copy(new FileInputStream(file), zos);
                                    zos.closeArchiveEntry();
                                } catch (IOException | InstantiationException | IllegalAccessException e) {
                                    throw new RuntimeException(e);
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
