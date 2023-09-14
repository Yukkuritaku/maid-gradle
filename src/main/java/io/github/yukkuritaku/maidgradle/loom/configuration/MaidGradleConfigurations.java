package io.github.yukkuritaku.maidgradle.loom.configuration;

import io.github.yukkuritaku.maidgradle.loom.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;

import javax.inject.Inject;

public abstract class MaidGradleConfigurations implements Runnable{

    @Inject
    protected abstract Project getProject();

    @Inject
    protected abstract ConfigurationContainer getConfigurations();

    @Inject
    protected abstract DependencyHandler getDependencies();

    @Inject
    protected abstract RepositoryHandler getRepositories();

    @Override
    public void run() {
        MaidGradleExtension maidGradleExtension = MaidGradleExtension.get(getProject());
        extendsFrom(MaidConstants.Configurations.FABRIC_MOD_IMPLEMENTATION, MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER);
        extendsFrom(MaidConstants.Configurations.FABRIC_MOD_IMPLEMENTATION, MaidConstants.Configurations.LITTLE_MAID_REBIRTH);
        getRepositories().flatDir(flatDirectoryArtifactRepository ->
                flatDirectoryArtifactRepository.dirs(
                        maidGradleExtension.getLMMLOutputDirectory().get().getAsFile(),
                        maidGradleExtension.getLMRBOutputDirectory().get().getAsFile()
                )
                );
        getDependencies().add(MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER,
                MaidConstants.Dependencies.getLittleMaidModelLoader(getProject()));
        getDependencies().add(MaidConstants.Configurations.LITTLE_MAID_REBIRTH,
                MaidConstants.Dependencies.getLittleMaidReBirth(getProject()));
    }

    public void extendsFrom(String a, String b) {
        getConfigurations().getByName(a, configuration -> configuration.extendsFrom(getConfigurations().getByName(b)));
    }
}
