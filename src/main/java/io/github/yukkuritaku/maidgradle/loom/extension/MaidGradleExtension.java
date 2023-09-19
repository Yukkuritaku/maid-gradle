package io.github.yukkuritaku.maidgradle.loom.extension;

import groovy.lang.GroovyObjectSupport;
import io.github.yukkuritaku.maidgradle.loom.api.ZipConfigExtensionAPI;
import net.fabricmc.loom.util.download.Download;
import net.fabricmc.loom.util.download.DownloadBuilder;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;

public abstract class MaidGradleExtension extends GroovyObjectSupport{

    protected final Project project;

    protected final Property<String> minecraftVersion;
    protected final Property<String> littleMaidModelLoader;
    protected final Property<String> littleMaidReBirthVersion;
    protected final Property<String> readMeFile;

    protected final ZipConfigExtensionAPI zipConfig;

    @Inject
    public MaidGradleExtension(final Project project){
        this.project = project;
        this.minecraftVersion = project.getObjects().property(String.class);
        this.littleMaidModelLoader = project.getObjects().property(String.class);
        this.littleMaidReBirthVersion = project.getObjects().property(String.class);
        this.readMeFile = project.getObjects().property(String.class);
        this.zipConfig = project.getObjects().newInstance(ZipConfigExtensionAPI.class);
        this.zipConfig.getUseNtfs().convention(true);
        this.zipConfig.getCompressionLevel().convention(Deflater.DEFAULT_COMPRESSION);
        this.zipConfig.getPngZipMode().convention(ZipEntry.STORED);
        this.zipConfig.getFolderZipMode().convention(ZipEntry.STORED);
        getLMMLOutputDirectory().convention(project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmml-jar")));
        getLMRBOutputDirectory().convention(project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmrb-jar")));
        getReadMeFile().convention("LittleMaidModel_ReadMe.txt");
    }

    private boolean manualRefreshDeps() {
        return project.getGradle().getStartParameter().isRefreshDependencies() || Boolean.getBoolean("loom.refresh");
    }

    public DownloadBuilder download(String url){
        DownloadBuilder builder;
        try {
            builder = Download.create(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to create downloader for: " + e);
        }
        if (project.getGradle().getStartParameter().isOffline()) {
            builder.offline();
        }
        if (manualRefreshDeps()) {
            builder.forceDownload();
        }
        return builder;
    }

    /**
     * Returns the value of Current Minecraft version.
     * @return Current Minecraft version
     */
    public abstract Property<String> getMinecraftVersion();

    /**
     * Set the value of minecraftVersion.
     * @param minecraftVersion your current minecraft version
     */
    public void minecraftVersion(String minecraftVersion){
        getMinecraftVersion().set(minecraftVersion);
    }

    /**
     * Returns the value of Current LittleMaidModelLoader version.
     * @return Current LittleMaidModelLoader version
     */
    public abstract Property<String> getLittleMaidModelLoaderVersion();

    /**
     * Set the value of LittleMaidModelLoader.
     * @param littleMaidModelLoaderVersion LittleMaidModelLoader version
     */
    public void littleMaidModelLoaderVersion(String littleMaidModelLoaderVersion){
        getLittleMaidModelLoaderVersion().set(littleMaidModelLoaderVersion);
    }

    /**
     * Returns the value of Current LittleMaidModelLoader output directory.
     * @return Current LittleMaidModel output directory, default output directory is build/lmml-jar
     */
    public abstract DirectoryProperty getLMMLOutputDirectory();

    /**
     * Returns the value of Current LittleMaidReBirth version.
     * @return Current LittleMaidReBirth version
     */
    public abstract Property<String> getLittleMaidReBirthVersion();

    /**
     * Set the value of LittleMaidReBirthVersion.
     * @param littleMaidReBirthVersion LittleMaidReBirthVersion version
     */
    public void littleMaidReBirthVersion(String littleMaidReBirthVersion){
        getLittleMaidReBirthVersion().set(littleMaidReBirthVersion);
    }

    /**
     * Returns the value of Current LittleMaidReBirth output directory.
     * @return Current LittleMaidReBirth output directory, default output directory is build/lmrb-jar
     */
    public abstract DirectoryProperty getLMRBOutputDirectory();

    /**
     * Returns the value of Current ReadMe file name and location.
     * @return Current ReadMe file location, default file name is LittleMaidModel_ReadMe.txt
     */
    public abstract Property<String> getReadMeFile();

    /**
     * Set the value of ReadMe file name.
     * @param readMeFile ReadMe file name
     */

    public void readMeFile(String readMeFile){
        getReadMeFile().set(readMeFile);
    }

    public ZipConfigExtensionAPI getZipConfig(){
        return this.zipConfig;
    }

    public void zipConfig(Action<ZipConfigExtensionAPI> action){
        action.execute(getZipConfig());
    }

}
