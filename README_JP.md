# Maid Gradle
このプロジェクトはまだ開発中です！

現在導入しているタスクはメイドさんのjarダウンロードと、zipビルドです。

## タスク紹介


## 導入方法

`settings.gradle`のpluginManagementにリポジトリとスクリプトを追加します。 
```gradle
pluginManagement {
	resolutionStrategy {
		eachPlugin {
			if(requested.id.toString() == "io.github.yukkuritaku.maid-gradle")
				useModule("com.github.Yukkuritaku.maid-gradle:io.github.yukkuritaku.maid-gradle.gradle.plugin:使いたいバージョン")
		}
	}
	repositories {
	    //jitpackを追加する
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
	id 'io.github.yukkuritaku.maid-gradle' version 'f6399d2de1'
	id 'maven-publish'
}

+ maidgradle {
+	//マイクラバージョン
+	minecraftVersion("${project.minecraft_version}")
+	//LittleMaidModelLoaderのバージョン
+	littleMaidModelLoaderVersion("${project.littlemaid_model_loader_version}")
+	//LittleMaidReBirthのバージョン
+	littleMaidReBirthVersion("${project.littlemaid_rebirth_version}")
+	//りーどみーファイルの指定(拡張子まで一致させる必要があるよ)
+	readMeFile("LittleMaidModel_Readme.txt")
+ }
```

