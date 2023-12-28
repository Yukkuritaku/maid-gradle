日本語の方のREADMEは[こちら](https://github.com/Yukkuritaku/maid-gradle/blob/master/README_JP.md)

# Maid Gradle
This project is gradle plugin extension of [Fabric Loom](https://github.com/FabricMC/fabric-loom).

Currently, implemented task is zip building and download jar.

## Tasks
Task groups are included in `maidgradle`.

- `buildLittleMaidModel`: build the littlemaid model. (create zip file with included build/classes and build/resources.)
- `downloadLittleMaidJars`: Download Littlemaid jar file. (this task is auto executing in reload the gradle.)
Downloaded Jar file are placed in build directory.

## Implement in your project

Create repository and script to pluginManagement in your `settings.gradle`.
```gradle
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if(requested.id.toString() == "io.github.yukkuritaku.maid-gradle")
                useModule("com.github.Yukkuritaku.maid-gradle:io.github.yukkuritaku.maid-gradle.gradle.plugin:(Latest Version))")
        }
    }
    repositories {
        // add jitpack repository
        maven { url = 'https://jitpack.io' }
        maven {
            name = 'Fabric'
            url = 'https://maven.fabricmc.net/'
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
```
Add `io.github.yukkuritaku.maid-gradle` below the `fabric-loom` plugin in your `build.gradle`.
```gradle
plugins{
    id 'fabric-loom' version '1.4-SNAPSHOT'
    // This should be below the fabric-loom plugin
    id 'io.github.yukkuritaku.maid-gradle'
    id 'maven-publish'
}

maidgradle {
    // Minecraft version
    minecraftVersion("(Minecraft version)")
    
    // LittleMaidModelLoader version
    littleMaidModelLoaderVersion("(LittleMaidModelLoader version)")
    
    // LittleMaidReBirth version
    littleMaidReBirthVersion("(LittleMaidReBirth version)")
   
    // Readme file
    readMeFile("Your Readme file")
}
```

All that is left is to reload the project and you are done!

## All maidgradle settings

```gradle
maidgradle {
    // Minecraft version
    minecraftVersion("(Minecraft version)")
    
    // LittleMaidModelLoader version
    littleMaidModelLoaderVersion("(LittleMaidModelLoader version)")
    
    // LittleMaidReBirth version
    littleMaidReBirthVersion("(LittleMaidReBirth version)")
   
    // Readme file
    readMeFile("Your Readme file")
    
    zipConfig {
       /*
        * File, Folder into zip with ntfs
        * default value is true
        */
        useNtfs.set(true)
       /*
        * Compression Level, settings value are 0 to 9, 
        * default value is Deflater.DEFAULT_COMPRESSION (-1)
        * Integers are fine.
        */
        compressionLevel.set(Deflater.DEFAULT_COMPRESSION)
        /*
         * Compression settings when zipping png.
         * setting values are ZipEntry.STORED (0, No compression) or ZipEntry.DEFLATED (8, Lossless Compression)
         * Integers are fine.
         * default value is ZipEntry.STORED (0)
         */
        pngZipMode.set(ZipEntry.STORED)
        /*
         * Compression settings when zipping folder.
         * setting values are ZipEntry.STORED (0, No compression) or ZipEntry.DEFLATED (8, Lossless Compression)
         * Integers are fine.
         * default value is ZipEntry.STORED (0)
         */
        folderZipMode.set(ZipEntry.STORED)
    }
}
```