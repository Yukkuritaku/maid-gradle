package io.github.yukkuritaku.maidgradle.loom.extension;

import groovy.lang.GroovyObjectSupport;
import net.fabricmc.loom.util.download.Download;
import net.fabricmc.loom.util.download.DownloadBuilder;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.net.URISyntaxException;

public abstract class MaidGradleExtension extends GroovyObjectSupport{

    protected final Project project;

    protected final Property<String> minecraftVersion;
    protected final Property<String> littleMaidModelLoader;
    protected final Property<String> littleMaidReBirthVersion;

    @Inject
    public MaidGradleExtension(final Project project){
        this.project = project;
        this.minecraftVersion = project.getObjects().property(String.class);
        this.littleMaidModelLoader = project.getObjects().property(String.class);
        this.littleMaidReBirthVersion = project.getObjects().property(String.class);

        getLMMLOutputDirectory().convention(project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmml-jar")));
        getLMRBOutputDirectory().convention(project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmrb-jar")));
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


}
