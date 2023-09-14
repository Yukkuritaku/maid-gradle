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

    @Inject
    public MaidGradleExtension(final Project project){
        this.project = project;
        getLMMLOutputDirectory().convention(project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmml-jar")));
        getLMRBOutputDirectory().convention(project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmrb-jar")));
    }

    public Project getProject() {
        return project;
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
    public abstract Property<String> getMcVersion();

    /**
     * Returns the value of Current LittleMaidModelLoader version.
     * @return Current LittleMaidModelLoader version
     */
    public abstract Property<String> getLittleMaidModelLoaderVersion();

    /**
     * Returns the value of Current LittleMaidModelLoader output directory.
     * @return Current LittleMaidModel output directory, default output directory is build/lmml-zip
     */
    public abstract DirectoryProperty getLMMLOutputDirectory();

    /**
     * Returns the value of Current LittleMaidReBirth version.
     * @return Current LittleMaidReBirth version
     */
    public abstract Property<String> getLittleMaidReBirthVersion();

    /**
     * Returns the value of Current LittleMaidReBirth output directory.
     * @return Current LittleMaidReBirth output directory, default output directory is build/lmrb-zip
     */
    public abstract DirectoryProperty getLMRBOutputDirectory();


}
