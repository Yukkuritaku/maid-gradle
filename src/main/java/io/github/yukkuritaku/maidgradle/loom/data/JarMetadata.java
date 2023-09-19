package io.github.yukkuritaku.maidgradle.loom.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record JarMetadata(@NotNull String jarUrl, @Nullable String devJarUrl, @Nullable String sourceJarUrl) {

    public static JarMetadata create(String jarUrl){
        return create(jarUrl, null);
    }

    public static JarMetadata create(String jarUrl, @Nullable String devJarUrl){
        return create(jarUrl, devJarUrl, null);
    }

    public static JarMetadata create(String jarUrl, @Nullable String devJarUrl, @Nullable String sourceJarUrl){
        return new JarMetadata(jarUrl, devJarUrl, sourceJarUrl);
    }
}
