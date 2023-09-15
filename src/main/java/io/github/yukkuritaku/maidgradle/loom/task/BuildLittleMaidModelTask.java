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

    private void setZipCompression(String fileName, ZipEntry entry, long crc32){
        if (fileName.endsWith(".class")){
            entry.setMethod(ZipOutputStream.DEFLATED);
        }else if (fileName.endsWith(".png")){
            //pngの時にSTOREに設定する、これをしないとメイドさんの画像を読み込めない
            //Deflate Descriptor UTF-8だとpngが読み込めないには何か理由があるんだろうか...
            //読み込めない原因を探るのに丸1日使った、ニッチ過ぎるバグやでこれ
            entry.setMethod(ZipOutputStream.STORED);
            entry.setCrc(crc32);
        }
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
    private void zipDirectory(int rootCount, Path path, ZipOutputStream zos) throws IOException {
        try (Stream<Path> paths = Files.list(path)) {
            paths.forEach(p -> {
                try {
                    var pathName = p.subpath(rootCount, p.getNameCount());
                    if (Files.isDirectory(p)) {
                        ZipEntry entry = new ZipEntry(pathName + "/");
                        zos.putNextEntry(entry);
                        zipDirectory(rootCount, p, zos);
                    } else {
                        var zipEntry = new ZipEntry(pathName.toString());
                        //setZipCompression(p.toFile().getName(), zipEntry, getCrc32(p.toFile()));
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
            zos.setLevel(Deflater.NO_COMPRESSION);
            sourceSetOutput.getFiles().forEach(file -> {
                        if (file.exists()) {
                            if (file.isDirectory()) {
                                try {
                                    zipDirectory(file.toPath().getNameCount(), file.toPath(), zos);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                                    var zipEntry = new ZipEntry(file.toPath().getFileName().toString());
                                    //setZipCompression(file.getName(), zipEntry, getCrc32(file));

                                    var attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
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
