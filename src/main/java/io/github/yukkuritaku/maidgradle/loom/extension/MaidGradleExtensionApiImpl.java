package io.github.yukkuritaku.maidgradle.loom.extension;

import io.github.yukkuritaku.maidgradle.loom.api.MaidGradleExtensionAPI;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

public abstract class MaidGradleExtensionApiImpl implements MaidGradleExtensionAPI {

    protected final Property<String> littleMaidModelLoaderVersion;

    protected final DirectoryProperty lmmlOutputDir;
    protected final Property<String> littleMaidReBirthVersion;
    protected final DirectoryProperty lmrbOutputDir;


    protected MaidGradleExtensionApiImpl(Project project){
        this.littleMaidModelLoaderVersion = project.getObjects().property(String.class);
        this.lmmlOutputDir = project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmml-zip"));
        this.littleMaidReBirthVersion = project.getObjects().property(String.class);
        this.lmrbOutputDir = project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmrb-zip"));
    }

    @Override
    public Property<String> getLittleMaidModelLoaderVersion() {
        return this.littleMaidModelLoaderVersion;
    }

    @Override
    public DirectoryProperty getLMMLOutputDirectory() {
        return lmmlOutputDir;
    }

    @Override
    public Property<String> getLittleReBirthVersion() {
        return this.littleMaidReBirthVersion;
    }

    @Override
    public DirectoryProperty getLMRBOutputDirectory() {
        return lmrbOutputDir;
    }
}
