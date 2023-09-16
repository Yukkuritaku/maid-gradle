# Maid Gradle
このプロジェクトはまだ開発中です！

現在導入しているタスクはメイドさんのjarダウンロードと、zipビルドです。

## タスク紹介

- `buildLittleMaidModel`: メイドさんのモデルをビルドします。(build/classesの中にあるファイルとbuild/resourcesの中にあるファイルをzip化します。)
- `downloadLittleMaidJars`: リトルメイドModのJarファイルをダウンロードします。
ダウンロードしたJarファイルはbuildディレクトリのlmml-dirとlmrb-dirにあります。
(このタスクはプロジェクトをリロードした時に自動で実行されます。)

## 導入方法

`settings.gradle`のpluginManagementにリポジトリとスクリプトを追加します。 
```gradle
pluginManagement {
    // これを追加しないとダウンロード出来ない
    resolutionStrategy {
        eachPlugin {
            if(requested.id.toString() == "io.github.yukkuritaku.maid-gradle")
                useModule("com.github.Yukkuritaku.maid-gradle:io.github.yukkuritaku.maid-gradle.gradle.plugin:使いたいバージョン")
        }
    }
    repositories {
        // jitpackを追加する
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
`build.gradle`のプラグインに`io.github.yukkuritaku.maid-gradle`を追加し、maidgradleの設定を追加します。
```gradle
plugins{
    id 'fabric-loom' version '1.3-SNAPSHOT'
    //適用するにはfabric-loomの下に設定する必要があります。
    id 'io.github.yukkuritaku.maid-gradle' version '使いたいバージョン'
    id 'maven-publish'
}

maidgradle {
    //マイクラバージョン
    minecraftVersion("(使用したいマイクラバージョン)")
    
    //LittleMaidModelLoaderのバージョン
    littleMaidModelLoaderVersion("(使用したいLittleMaidModelLoaderのバージョン)")
    
    //LittleMaidReBirthのバージョン
    littleMaidReBirthVersion("(使用したいLittleMaidReBirthのバージョン)")
    
    //りーどみーファイルの指定(拡張子まで一致させる必要があるよ)
    readMeFile("LittleMaidModel_Readme.txt")
}
```
後はプロジェクトをリロードすれば完了です！


## maidgradleの全体設定

```gradle
maidgradle {
    //マイクラバージョン
    minecraftVersion("(使用したいマイクラバージョン)")
    
    //LittleMaidModelLoaderのバージョン
    littleMaidModelLoaderVersion("(使用したいLittleMaidModelLoaderのバージョン)")
    
    //LittleMaidReBirthのバージョン
    littleMaidReBirthVersion("(使用したいLittleMaidReBirthのバージョン)")
    
    //りーどみーファイルの指定(拡張子まで一致させる必要があるよ)
    readMeFile("LittleMaidModel_Readme.txt")
    
    zipConfig {
       /*
        * ファイル、フォルダーをntfsでzip化するか
        * デフォルト値はtrue
        */
        useNtfs.set(true)
       /*
        * 圧縮レベル 0から9までのレベルを指定可能で、デフォルト値はDeflater.DEFAULT_COMPRESSION (-1)
        * 整数でもおっけー
        */
        compressionLevel.set(Deflater.DEFAULT_COMPRESSION)
        /*
         * pngをzip化する時の圧縮設定
         * 設定できる値は ZipEntry.STORED (0、無圧縮) か ZipEntry.DEFLATED (8、可逆圧縮)
         * 整数でもおっけー
         * デフォルト値はZipEntry.STORED (0)
         */
        pngZipMode.set(ZipEntry.STORED)
        /*
         * フォルダーをzip化する時の圧縮設定
         * 設定できる値は ZipEntry.STORED (0、無圧縮) か ZipEntry.DEFLATED (8、可逆圧縮)
         * 整数でもおっけー
         * デフォルト値はZipEntry.STORED (0)
         */
        folderZipMode.set(ZipEntry.STORED)
    }
}
```