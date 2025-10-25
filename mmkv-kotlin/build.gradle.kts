import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.vanniktech.maven.publish)
}

version = "1.3.1"
group = "com.ctrip.flight.mmkv"

@OptIn(ExperimentalKotlinGradlePluginApi::class)
kotlin {
    jvmToolchain(21)
    androidTarget {
        publishLibraryVariants("release")
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "13.0"
        osx.deploymentTarget = "10.15"
        framework {
            baseName = "MMKV-Kotlin"
            isStatic = true
        }
        pod(
            name = "MMKV",
            version = libs.versions.mmkv.get(),
        )
    }
    
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            api(libs.mmkv)
        }
        androidInstrumentedTest.dependencies {
            implementation(kotlin("test-junit"))
            implementation(libs.junit)
            implementation(libs.androidx.test.core)
            implementation(libs.androidx.test.runner)
            implementation(libs.androidx.test.rules)
        }
    }
}

android {
    namespace = "com.ctrip.flight.mmkv"
    compileSdk = 36
    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = group.toString(),
        artifactId = "mmkv-kotlin",
        version = version.toString(),
    )

    pom {
        name.set("MMKV-Kotlin")
        description.set("MMKV for Kotlin Multiplatform")
        url.set("https://github.com/ctripcorp/mmkv-kotlin")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("qiaoyuang")
                name.set("Yuang Qiao")
                email.set("qiaoyuang2012@gmail.com")
            }
        }
        scm {
            url.set("https://github.com/ctripcorp/mmkv-kotlin")
            connection.set("scm:git:https://github.com/ctripcorp/mmkv-kotlin.git")
            developerConnection.set("scm:git:https://github.com/ctripcorp/mmkv-kotlin.git")
        }
    }
}