package io.github.yukkuritaku.maidgradle.loom.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record JarMetadata(@NotNull String jarUrl, @Nullable String devJarUrl, @Nullable String sourceJarUrl) {}
