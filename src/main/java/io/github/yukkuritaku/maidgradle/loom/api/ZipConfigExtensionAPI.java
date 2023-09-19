package io.github.yukkuritaku.maidgradle.loom.api;

import org.gradle.api.provider.Property;

public interface ZipConfigExtensionAPI {
    /**
     * Returns the value of current use ntfs option.
     * @return current use ntfs option
     */
    Property<Boolean> getUseNtfs();

    /**
     * Returns the value of current compression level.
     * @return current compression level
     */
    Property<Integer> getCompressionLevel();
    /**
     * Returns the value of current png zip mode.
     * @return current png zip mode
     */
    Property<Integer> getPngZipMode();
    /**
     * Returns the value of current folder zip mode.
     * @return current folder zip mode
     */
    Property<Integer> getFolderZipMode();

}
