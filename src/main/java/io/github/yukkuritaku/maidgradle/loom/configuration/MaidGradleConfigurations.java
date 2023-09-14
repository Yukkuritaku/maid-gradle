package io.github.yukkuritaku.maidgradle.loom.configuration;

import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.util.MaidConstants;
import net.fabricmc.loom.util.download.DownloadExecutor;
import net.fabricmc.loom.util.download.GradleDownloadProgressListener;
import net.fabricmc.loom.util.gradle.ProgressGroup;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.dsl.DependencyHandler;

import javax.inject.Inject;
import java.io.IOException;

public abstract class MaidGradleConfigurations implements Runnable {

    @Inject
    protected abstract Project getProject();

    @Inject
    protected abstract ConfigurationContainer getConfigurations();

    @Inject
    protected abstract DependencyHandler getDependencies();

    @Override
    public void run() {
        final MaidGradleExtension maidGradleExtension = getProject().getExtensions().getByType(MaidGradleExtension.class);
        /*register(MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER, Role.RESOLVABLE);
        register(MaidConstants.Configurations.LITTLE_MAID_REBIRTH, Role.RESOLVABLE);
        extendsFrom(MaidConstants.Configurations.FABRIC_MOD_IMPLEMENTATION, MaidConstants.Configurations.LITTLE_MAID_MODEL_LOADER);
        extendsFrom(MaidConstants.Configurations.FABRIC_MOD_IMPLEMENTATION, MaidConstants.Configurations.LITTLE_MAID_REBIRTH);*/

        getProject().getLogger().lifecycle("Download LittleMaid Dependencies...");
        try (ProgressGroup progressGroup = new ProgressGroup(getProject(), "Download LittleMaidModelLoader");
             DownloadExecutor executor = new DownloadExecutor(2)
        ) {
            String versionInfo = maidGradleExtension.getMcVersion().get();
            maidGradleExtension
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMMLDownloadUrl(versionInfo, maidGradleExtension))
                    .progress(new GradleDownloadProgressListener("LittleMaidModelLoader", progressGroup::createProgressLogger))
                    .downloadPathAsync(maidGradleExtension.getLMMLOutputDirectory().get().file(
                            "LMML-" + versionInfo + "-" + maidGradleExtension.getLittleMaidModelLoaderVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath(), executor);
            maidGradleExtension
                    .download(MaidConstants.LittleMaidJarFileUrls.getLMRBDownloadUrl(versionInfo, maidGradleExtension))
                    .progress(new GradleDownloadProgressListener("LittleMaidReBirth", progressGroup::createProgressLogger))
                    .downloadPathAsync(maidGradleExtension.getLMRBOutputDirectory().get().file(
                            "LMRB-" + versionInfo + "-" + maidGradleExtension.getLittleMaidReBirthVersion().get() + "-Fabric.jar"
                    ).getAsFile().toPath(), executor);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        getProject().getLogger().lifecycle("Done!");
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
