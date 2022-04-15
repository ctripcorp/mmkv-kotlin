plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlin-parcelize")
    id("maven-publish")
}

version = "1.0.0"
group = "com.ctrip.flight.mmkv"

kotlin {
    android {
        publishLibraryVariants("release")
    }
    iosX64 {
        setupIOSConfig(this)
    }
    iosArm64 {
        setupIOSConfig(this)
    }
    iosSimulatorArm64 {
        setupIOSConfig(this)
    }

    publishing {
        publications {
            val publicationsFromMainHost = listOf(android()).map { it.name } + "kotlinMultiplatform"
            matching { it.name in publicationsFromMainHost }.all {
                val targetPublication = this@all
                tasks.withType<AbstractPublishToMaven>()
                    .matching { it.publication == targetPublication }
                    .configureEach { onlyIf { findProperty("isMainHost") == "true" } }
            }
        }
        repositories {
            maven {
                // 审核通过后补充...
            }
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "MMKV-Kotlin"
        }
        pod(
            name = "MMKV",
            version = "1.2.12",
        )
    }
    
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("com.tencent:mmkv-static:1.2.12")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:core:1.4.0")
                implementation("androidx.test:runner:1.4.0")
                implementation("androidx.test:rules:1.4.0")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 31
    buildToolsVersion = "31.0.0"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets.getByName("androidTest") {
        manifest.srcFile(File("src/androidTest/AndroidManifest.xml"))
        java.setSrcDirs(listOf("src/androidTest/kotlin"))
    }
    defaultConfig {
        minSdk = 23
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

fun setupIOSConfig(target: org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget) {
    target.compilations["main"].kotlinOptions.freeCompilerArgs += listOf("-Xallocator=mimalloc", "-Xruntime-logs=gc=info", "-Xexport-kdoc")
    target.binaries {
        /*all {
            binaryOptions["memoryModel"] = "experimental"
            binaryOptions["freezing"] = "disabled"
        }*/
    }
}