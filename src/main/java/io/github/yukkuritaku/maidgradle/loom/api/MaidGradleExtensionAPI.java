package io.github.yukkuritaku.maidgradle.loom.api;

import org.gradle.api.artifacts.Dependency;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

/**
 * This is the public api available exposed to build scripts.
 */
public interface MaidGradleExtensionAPI {

    /**
     * Returns the value of Current LittleMaidModelLoader version.
     * @return Current LittleMaidModelLoader version
     */
    Property<String> getLittleMaidModelLoaderVersion();

    /**
     * Returns the value of Current LittleMaidModelLoader output directory.
     * @return Current LittleMaidModel output directory, default output directory is build/lmml-zip
     */
    DirectoryProperty getLMMLOutputDirectory();

    /**
     * Returns the value of Current LittleMaidReBirth version.
     * @return Current LittleMaidReBirth version
     */
    Property<String> getLittleReBirthVersion();


    /**
     * Returns the value of Current LittleMaidReBirth output directory.
     * @return Current LittleMaidReBirth output directory, default output directory is build/lmrb-zip
     */
    DirectoryProperty getLMRBOutputDirectory();
}
