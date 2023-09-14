package io.github.yukkuritaku.maidgradle.loom.extension;

import io.github.yukkuritaku.maidgradle.loom.MaidGradleExtension;
import net.fabricmc.loom.util.download.Download;
import net.fabricmc.loom.util.download.DownloadBuilder;
import org.gradle.api.Project;

import java.net.URISyntaxException;

public class MaidGradleExtensionImpl extends MaidGradleExtensionApiImpl implements MaidGradleExtension {

    private final Project project;
    public MaidGradleExtensionImpl(Project project) {
        super(project);
        this.project = project;
    }

    @Override
    public DownloadBuilder download(String url) {
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

    private boolean manualRefreshDeps() {
        return project.getGradle().getStartParameter().isRefreshDependencies() || Boolean.getBoolean("loom.refresh");
    }
}
