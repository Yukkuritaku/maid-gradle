package io.github.yukkuritaku.maidgradle.loom.task;

import net.fabricmc.loom.util.gradle.SourceSetHelper;
import org.apache.commons.compress.archivers.ArchiveEntry;
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
import java.util.stream.Stream;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
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

    private enum FileType{
        CLASS(".class", ZipOutputStream.DEFLATED),
        PNG(".png", ZipOutputStream.STORED);

        private final String endsWith;
        private final int method;

        FileType(String endsWith, int method){
            this.endsWith = endsWith;
            this.method = method;
        }

        public String getEndsWith() {
            return endsWith;
        }

        public int getMethod() {
            return method;
        }
    }

    private void setZipCompression(File file, ZipEntry entry) throws IOException {
        /*if (file.getName().endsWith(".class")){
            entry.setMethod(ZipOutputStream.DEFLATED);
        }else if (file.getName().endsWith(".png")){
            //pngの時にSTOREに設定する、これをしないとメイドさんの画像を読み込めない
            //Deflate Descriptor UTF-8だとpngが読み込めないには何か理由があるんだろうか...
            //読み込めない原因を探るのに丸1日使った、ニッチ過ぎるバグやでこれ
            entry.setMethod(ZipOutputStream.STORED);
            entry.setCrc(getCrc32(file));
            entry.setSize(Files.size(file.toPath()));
        }*/
    }

    private static long getCrc32(File file) throws IOException {
        CRC32 crc32 = new CRC32();
        try(FileInputStream fis = new FileInputStream(file)) {
            byte[] b = new byte[1024];
            int len = fis.read(b);
            while (len != -1){
                crc32.update(b, 0, len);
                len = fis.read(b);
            }
        }
        return crc32.getValue();
    }

    /**
     * Taken from <a href="https://qiita.com/ry-s/items/961e295b74edb39768d0">ry-s(R S)'s qiita blog</a>
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
                        zos.putArchiveEntry(entry);
                        zos.closeArchiveEntry();
                        zipDirectory(rootCount, p, zos);
                    } else {
                        var zipEntry = new ZipArchiveEntry(pathName.toString());
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
        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(getOutputDir().file(outputName).get().getAsFile())))) {
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
