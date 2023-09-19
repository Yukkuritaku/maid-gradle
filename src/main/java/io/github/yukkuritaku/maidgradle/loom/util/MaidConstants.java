package io.github.yukkuritaku.maidgradle.loom.util;

import io.github.yukkuritaku.maidgradle.loom.MaidGradlePlugin;
import io.github.yukkuritaku.maidgradle.loom.data.JarMetadata;
import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import org.gradle.api.Project;

import javax.annotation.Nullable;
import java.util.Map;

public final class MaidConstants {

    public static final String MAID_GRADLE = "maidgradle";
    public static final String VERSION = "0.1";

    public static class LittleMaidJarFileUrls{

        //TODO Map使いまくるのもあれだからもっと簡易的にしたい

        /*private static final Map<String, Map<String, String>> LMML_DROPBOX_JAR_MAPPING = Map.of(
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
        );*/


        private static Map<String, Map<String, JarMetadata>> LMML_JAR_URL_MAPPING;
        private static Map<String, Map<String, JarMetadata>> LMRB_JAR_URL_MAPPING;


        public static void setLmmlJarUrlMapping(Map<String, Map<String, JarMetadata>> lmmlJarUrlMapping) {
            LMML_JAR_URL_MAPPING = lmmlJarUrlMapping;
        }

        public static void setLmrbJarUrlMapping(Map<String, Map<String, JarMetadata>> lmrbJarUrlMapping) {
            LMRB_JAR_URL_MAPPING = lmrbJarUrlMapping;
        }

        public static String versionConvert(String searchVersion){
            if (searchVersion.contains("1.20.1")){
                searchVersion = "1.20";
            }
            return searchVersion;
        }

        @Nullable
        public static String getLMMLDownloadUrl(String searchVersion, String lmmlVersion){
            JarMetadata lmml = LMML_JAR_URL_MAPPING.get(versionConvert(searchVersion)).get(lmmlVersion);
            return lmml != null ? lmml.jarUrl() + "?dl=1" : null;
        }

        @Nullable
        public static String getLMMLDevDownloadUrl(String searchVersion, String lmmlVersion){
            JarMetadata lmml = LMML_JAR_URL_MAPPING.get(versionConvert(searchVersion)).get(lmmlVersion);
            return lmml != null && lmml.devJarUrl() != null ? lmml.devJarUrl() + "?dl=1" : null;
        }

        @Nullable
        public static String getLMMLSourceDownloadUrl(String searchVersion, String lmmlVersion){
            JarMetadata lmml = LMML_JAR_URL_MAPPING.get(versionConvert(searchVersion)).get(lmmlVersion);
            return lmml != null && lmml.sourceJarUrl() != null ? lmml.sourceJarUrl() + "?dl=1" : null;
        }

        @Nullable
        public static String getLMRBDownloadUrl(String searchVersion, String lmrbVersion){
            JarMetadata lmrb = LMRB_JAR_URL_MAPPING.get(versionConvert(searchVersion)).get(lmrbVersion);
            return lmrb != null ? lmrb.jarUrl() + "?dl=1" : null;
        }

        @Nullable
        public static String getLMRBDevDownloadUrl(String searchVersion, String lmrbVersion){
            JarMetadata lmrb = LMRB_JAR_URL_MAPPING.get(versionConvert(searchVersion)).get(lmrbVersion);
            return lmrb != null && lmrb.devJarUrl() != null ? lmrb.devJarUrl() + "?dl=1" : null;
        }

        @Nullable
        public static String getLMRBSourceDownloadUrl(String searchVersion, String lmrbVersion){
            JarMetadata lmrb = LMRB_JAR_URL_MAPPING.get(versionConvert(searchVersion)).get(lmrbVersion);
            return lmrb != null && lmrb.sourceJarUrl() != null ? lmrb.sourceJarUrl() + "?dl=1" : null;
        }

        /*@Nullable
        public static String getLMMLDownloadUrl(String searchVersion, String lmmlVersion){
            JarMetadata lmml = LMML_JAR_URL_MAPPING.get(versionConvert(searchVersion)).get(lmmlVersion);
            return lmml != null ? lmml + "?dl=1" : null;
        }

        @Nullable
        public static String getLMRBDownloadUrl(String searchVersion, String lmrbVersion){
            String lmrb = LMRB_JAR_URL_MAPPING.get(versionConvert(searchVersion)).get(lmrbVersion);
            return lmrb != null ? lmrb + "?dl=1" : null;
        }*/
    }

    public static class Configurations{

        public static final String FABRIC_MOD_IMPLEMENTATION = "modImplementation";
        public static final String FABRIC_MOD_API = "modApi";

        public static final String LITTLE_MAID_MODEL_LOADER = "littleMaidModelLoader";
        public static final String LITTLE_MAID_REBIRTH = "littleMaidReBirth";

        public static final String MOD_LITTLE_MAID_MODEL_LOADER = "modLittleMaidModelLoader";
        public static final String MOD_LITTLE_MAID_REBIRTH = "modLittleMaidReBirth";
    }

    public static class Dependencies{

        public static final String LITTLE_MAID_JAR_PACKAGE_NAME = "net.sistr";

        public static final String LITTLE_MAID_MODEL_LOADER = LITTLE_MAID_JAR_PACKAGE_NAME + ":LMML";
        public static final String LITTLE_MAID_REBIRTH = LITTLE_MAID_JAR_PACKAGE_NAME + ":LMRB";

        public static String getLittleMaidModelLoader(Project project){
            final MaidGradleExtension maidGradleExtension = project.getExtensions().getByType(MaidGradleExtension.class);
            return LITTLE_MAID_MODEL_LOADER +
                    "-" + maidGradleExtension.getMinecraftVersion().get() +
                    "-" + maidGradleExtension.getLittleMaidModelLoaderVersion().get() +
                    "-Fabric";
        }

        public static String getLittleMaidReBirth(Project project){
            final MaidGradleExtension maidGradleExtension = project.getExtensions().getByType(MaidGradleExtension.class);
            return LITTLE_MAID_REBIRTH +
                    "-" + maidGradleExtension.getMinecraftVersion().get() +
                    "-" + maidGradleExtension.getLittleMaidReBirthVersion().get() +
                    "-Fabric";
        }
    }

}


