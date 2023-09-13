package io.github.yukkuritaku.maidgradle.loom.util;

import io.github.yukkuritaku.maidgradle.loom.MaidGradleExtension;
import io.github.yukkuritaku.maidgradle.loom.api.MaidGradleExtensionAPI;
import net.fabricmc.loom.LoomGradleExtension;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftVersionMeta;
import org.gradle.api.Project;

import javax.annotation.Nullable;
import java.util.Map;

public final class MaidConstants {

    public static final String MAID_GRADLE = "maidgradle";
    public static final String VERSION = "0.1";

    public static class LittleMaidJarFileUrls{

        private static final Map<String, Map<String, String>> LMML_DROPBOX_JAR_MAPPING = Map.of(
                "1.18.2",
                Map.of("4.6.4", "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAAFen2Ol-8sEhk8nEyfqlura/LittleMaidModelLoader/Fabric/1.18.x/LMML-1.18.2-4.6.4-Fabric.jar"),
                "1.19.2",
                Map.of("5.2.3", "https://www.dropbox.com/sh/tzkdz46y67tuohx/AAChwEiTvinWk9wyejgZpFSNa/LittleMaidModelLoader/Fabric/1.19.2/LMML-1.19.2-5.2.3-Fabric.jar"),
                "1.19.3",
                Map.of("6.0.2", "https://www.dropbox.com/sh/tzkdz46y67tuohx/AADTTZblojACvCmC3zkmMRNoa/LittleMaidModelLoader/Fabric/1.19.3/LMML-1.19.3-6.0.2-Fabric.jar"),
                "1.19.4",
                Map.of("7.0.1", "https://www.dropbox.com/sh/tzkdz46y67tuohx/AACfN2ElnIk5E0Q6utFlXalsa/LittleMaidModelLoader/Fabric/1.19.4/LMML-1.19.4-7.0.1-Fabric.jar"),
                "1.20",
                Map.of("8.0.0", "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABd4Q-tbT3q6EXTUhCpWfE0a/LittleMaidModelLoader/Fabric/1.20/LMML-1.20.1-8.0.0-Fabric.jar")
        );

        private static final Map<String, Map<String, String>> LMRB_DROPBOX_JAR_MAPPING = Map.of(
                "1.20",
                Map.of("8.0.8", "https://www.dropbox.com/sh/tzkdz46y67tuohx/AABsUzcvz87L8DNqi5iqSYsua/LittleMaidReBirth/Fabric/1.20/LMRB-1.20.1-8.0.8-Fabric.jar")
        );

        public static String versionConvert(String searchVersion){
            if (searchVersion.contains("1.16")){
                searchVersion = "1.16";
            }else if (searchVersion.contains("1.17")){
                searchVersion = "1.17";
            }else if (searchVersion.contains("1.18")){
                searchVersion = "1.18";
            }else if (searchVersion.contains("1.19.2")){
                searchVersion = "1.19.2";
            }else if (searchVersion.contains("1.19.3")){
                searchVersion = "1.19.3";
            }else if (searchVersion.contains("1.19.4")){
                searchVersion = "1.19.4";
            }else if (searchVersion.contains("1.20")){
                searchVersion = "1.20";
            }
            return searchVersion;
        }

        @Nullable
        public static String getLMMLDownloadUrl(String searchVersion, MaidGradleExtensionAPI extension){
            String lmml = LMML_DROPBOX_JAR_MAPPING.get(versionConvert(searchVersion)).get(extension.getLittleMaidModelLoaderVersion().get());
            return lmml != null ? lmml + "?dl=1" : null;
        }

        @Nullable
        public static String getLMRBDownloadUrl(String searchVersion, MaidGradleExtensionAPI extension){
            String lmrb = LMRB_DROPBOX_JAR_MAPPING.get(versionConvert(searchVersion)).get(extension.getLittleReBirthVersion().get());
            return lmrb != null ? lmrb + "?dl=1" : null;
        }
    }

    public static class Configurations{

        public static final String FABRIC_MOD_IMPLEMENTATION = "modImplementation";

        public static final String LITTLE_MAID_MODEL_LOADER = "littleMaidModelLoader";
        public static final String LITTLE_MAID_REBIRTH = "littleMaidReBirth";
    }

    public static class Dependencies{

        public static final String LITTLE_MAID_JAR_PACKAGE_NAME = "net.sistr";

        public static final String LITTLE_MAID_MODEL_LOADER = LITTLE_MAID_JAR_PACKAGE_NAME + ":LMML";
        public static final String LITTLE_MAID_REBIRTH = LITTLE_MAID_JAR_PACKAGE_NAME + ":LMRB";

        public static String getLittleMaidModelLoader(Project project){
            MaidGradleExtension maidGradleExtension = MaidGradleExtension.get(project);
            LoomGradleExtension loomGradleExtension = LoomGradleExtension.get(project);
            MinecraftVersionMeta versionMeta = loomGradleExtension.getMinecraftProvider().getVersionInfo();
            return LITTLE_MAID_MODEL_LOADER +
                    "-" + versionMeta.id() +
                    "-" + maidGradleExtension.getLittleMaidModelLoaderVersion().get() +
                    "-Fabric";
        }

        public static String getLittleMaidReBirth(Project project){
            MaidGradleExtension maidGradleExtension = MaidGradleExtension.get(project);
            LoomGradleExtension loomGradleExtension = LoomGradleExtension.get(project);
            MinecraftVersionMeta versionMeta = loomGradleExtension.getMinecraftProvider().getVersionInfo();
            return LITTLE_MAID_REBIRTH +
                    "-" + versionMeta.id() +
                    "-" + maidGradleExtension.getLittleMaidModelLoaderVersion().get() +
                    "-Fabric";
        }
    }

}


