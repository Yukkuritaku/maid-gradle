package io.github.yukkuritaku.maidgradle.loom.util;

import io.github.yukkuritaku.maidgradle.loom.data.JarMetadata;
import io.github.yukkuritaku.maidgradle.loom.extension.MaidGradleExtension;
import org.gradle.api.Project;

import javax.annotation.Nullable;
import java.util.Map;

public final class MaidConstants {

    public static final String MAID_GRADLE = "maidgradle";

    public static class LittleMaidJarFileUrls{

        private static Map<String, Map<String, JarMetadata>> LMML_JAR_URL_MAPPING;
        private static Map<String, Map<String, JarMetadata>> LMRB_JAR_URL_MAPPING;


        public static void setLmmlJarUrlMapping(Map<String, Map<String, JarMetadata>> lmmlJarUrlMapping) {
            LMML_JAR_URL_MAPPING = lmmlJarUrlMapping;
        }

        public static void setLmrbJarUrlMapping(Map<String, Map<String, JarMetadata>> lmrbJarUrlMapping) {
            LMRB_JAR_URL_MAPPING = lmrbJarUrlMapping;
        }

        public static String versionConvert(String searchVersion){
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
    }

    public static class Configurations{

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
            String minecraftVersion = maidGradleExtension.getMinecraftVersion().get();
            String lmmlVersion = maidGradleExtension.getLittleMaidModelLoaderVersion().get();
            String lmmlDownloadUrl = LittleMaidJarFileUrls.getLMMLDownloadUrl(minecraftVersion, lmmlVersion);
            boolean isFaArc = lmmlDownloadUrl.contains("Fa-Arc");
            String libName = LITTLE_MAID_MODEL_LOADER +
                    (isFaArc ? "-Fa-Arc-" : "-") + minecraftVersion + "-" + lmmlVersion + (isFaArc ? "" : "-Fabric");
            if (minecraftVersion.equalsIgnoreCase("1.16.4") ||
                    minecraftVersion.equalsIgnoreCase("1.16.3")){
                libName = LITTLE_MAID_MODEL_LOADER + "-Fabric-" + minecraftVersion + "-" + lmmlVersion;
            }

            return libName;
        }

        public static String getLittleMaidReBirth(Project project){
            final MaidGradleExtension maidGradleExtension = project.getExtensions().getByType(MaidGradleExtension.class);
            String minecraftVersion = maidGradleExtension.getMinecraftVersion().get();
            String lmrbVersion = maidGradleExtension.getLittleMaidReBirthVersion().get();
            String lmrbDownloadUrl = LittleMaidJarFileUrls.getLMRBDownloadUrl(minecraftVersion, lmrbVersion);
            boolean isFaArc = lmrbDownloadUrl.contains("Fa-Arc");
            String libName = LITTLE_MAID_REBIRTH +
                    (isFaArc ? "-Fa-Arc-" : "-") + minecraftVersion + "-" + lmrbVersion + (isFaArc ? "" : "-Fabric");
            if (minecraftVersion.equalsIgnoreCase("1.16.4") ||
                    minecraftVersion.equalsIgnoreCase("1.16.3")){
                libName = LITTLE_MAID_REBIRTH + "-Fabric-" + minecraftVersion + "-" + lmrbVersion;
            }
            return libName;
        }
    }

}


