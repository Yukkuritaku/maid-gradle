package io.github.yukkuritaku.maidgradle.loom.extension;

import io.github.yukkuritaku.maidgradle.loom.api.MaidGradleExtensionAPI;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

public abstract class MaidGradleExtensionApiImpl implements MaidGradleExtensionAPI {

    protected final Property<String> mcVersion;
    protected final Property<String> littleMaidModelLoaderVersion;
    protected final DirectoryProperty lmmlOutputDir;
    protected final Property<String> littleMaidReBirthVersion;
    protected final DirectoryProperty lmrbOutputDir;

    @Inject
    public MaidGradleExtensionApiImpl(Project project){
        this.mcVersion = project.getObjects().property(String.class);
        this.littleMaidModelLoaderVersion = project.getObjects().property(String.class);
        this.lmmlOutputDir = project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmml-jar"));
        this.littleMaidReBirthVersion = project.getObjects().property(String.class);
        this.lmrbOutputDir = project.getObjects().directoryProperty().convention(project.getLayout().getBuildDirectory().dir("lmrb-jar"));
    }

    @Override
    public Property<String> getMcVersion() {
        return this.mcVersion;
    }

    @Override
    public Property<String> getLittleMaidModelLoaderVersion() {
        return this.littleMaidModelLoaderVersion;
    }

    @Override
    public DirectoryProperty getLMMLOutputDirectory() {
        return this.lmmlOutputDir;
    }

    @Override
    public Property<String> getLittleMaidReBirthVersion() {
        return this.littleMaidReBirthVersion;
    }

    @Override
    public DirectoryProperty getLMRBOutputDirectory() {
        return this.lmrbOutputDir;
    }
}
