package io.github.yukkuritaku.maidgradle.loom.configuration;

import groovy.lang.Closure;
import io.github.yukkuritaku.maidgradle.loom.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.configuration.LoomConfigurations;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;

import javax.inject.Inject;

public abstract class MaidGradleConfigurations implements Runnable {

    @Inject
    protected abstract Project getProject();

    @Inject
    protected abstract ConfigurationContainer getConfigurations();

    @Inject
    protected abstract DependencyHandler getDependencies();

    @Override
    public void run() {
        MaidGradleExtension maidGradleExtension = MaidGradleExtension.get(getProject());
        /*register(MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER, Role.RESOLVABLE);
        register(MaidConstants.Configurations.LITTLE_MAID_REBIRTH, Role.RESOLVABLE);
        extendsFrom(MaidConstants.Configurations.FABRIC_MOD_IMPLEMENTATION, MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER);
        extendsFrom(MaidConstants.Configurations.FABRIC_MOD_IMPLEMENTATION, MaidConstants.Configurations.LITTLE_MAID_REBIRTH);*/
        getProject().getRepositories().add(getProject().getRepositories().flatDir(flatDirectoryArtifactRepository -> {
                    flatDirectoryArtifactRepository.dir(
                            "build/" + maidGradleExtension.getLMMLOutputDirectory().get().getAsFile().getName()
                    );

                    flatDirectoryArtifactRepository.dir(
                            "build/" + maidGradleExtension.getLMRBOutputDirectory().get().getAsFile().getName()
                    );
                }
        ));

        /*getDependencies().add(MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER,
                MaidConstants.Dependencies.getLittleMaidModelLoader(getProject()));
        getDependencies().add(MaidConstants.Configurations.LITTLE_MAID_REBIRTH,
                MaidConstants.Dependencies.getLittleMaidReBirth(getProject()));*/
    }

    private NamedDomainObjectProvider<Configuration> register(String name, Role role) {
        return getConfigurations().register(name, role::apply);
    }

    public void extendsFrom(String a, String b) {
        getConfigurations().getByName(a, configuration -> configuration.extendsFrom(getConfigurations().getByName(b)));
    }

    enum Role {
        NONE(false, false),
        CONSUMABLE(true, false),
        RESOLVABLE(false, true);

        private final boolean canBeConsumed;
        private final boolean canBeResolved;

        Role(boolean canBeConsumed, boolean canBeResolved) {
            this.canBeConsumed = canBeConsumed;
            this.canBeResolved = canBeResolved;
        }

        void apply(Configuration configuration) {
            configuration.setCanBeConsumed(canBeConsumed);
            configuration.setCanBeResolved(canBeResolved);
        }
    }
}
