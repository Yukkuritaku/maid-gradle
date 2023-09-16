package io.github.yukkuritaku.maidgradle.loom.api;

import org.gradle.api.provider.Property;

public interface ZipConfigExtensionAPI {
    Property<Boolean> getUseNtfs();
    Property<Integer> getCompressionLevel();
    Property<Integer> getPngZipMode();
    Property<Integer> getFolderZipMode();

}
