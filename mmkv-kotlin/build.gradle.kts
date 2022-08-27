import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlin-parcelize")
    id("maven-publish")
    signing
}

version = "1.2.2"
group = "com.ctrip.flight.mmkv"

val NEXUS_USERNAME: String by project
val NEXUS_PASSWORD: String by project

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

kotlin {
    android {
        publishLibraryVariants("release")
    }
    iosX64 {
        setupNativeConfig()
    }
    iosArm64 {
        setupNativeConfig()
    }
    iosSimulatorArm64 {
        setupNativeConfig()
    }
    macosX64 {
        setupNativeConfig()
    }
    macosArm64 {
        setupNativeConfig()
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        osx.deploymentTarget = "12.2.1"
        framework {
            baseName = "MMKV-Kotlin"
        }
        pod(
            name = "MMKV",
            version = "1.2.14",
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
                api("com.tencent:mmkv-static:1.2.14")
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
        val macosX64Main by getting
        val macosArm64Main by getting
        val appleMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            macosX64Main.dependsOn(this)
            macosArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val macosX64Test by getting
        val macosArm64Test by getting
        val appleTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
            macosX64Test.dependsOn(this)
            macosArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    buildToolsVersion = "32.0.0"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets.getByName("androidTest") {
        manifest.srcFile(File("src/androidTest/AndroidManifest.xml"))
        java.setSrcDirs(listOf("src/androidTest/kotlin"))
    }
    defaultConfig {
        minSdk = 23
        targetSdk = 32
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

publishing {
    publications.withType<MavenPublication> {
        artifact(javadocJar)
        with(pom) {
            name.set("MMKV-Kotlin")
            description.set("MMKV for Kotlin Multiplatform")
            url.set("https://github.com/ctripcorp/mmkv-kotlin")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
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
    repositories {
        maven {
            credentials {
                username = NEXUS_USERNAME
                password = NEXUS_PASSWORD
            }
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
        }
    }
    signing {
        sign(publishing.publications)
    }
}

fun KotlinNativeTarget.setupNativeConfig() {
    compilations["main"].kotlinOptions.freeCompilerArgs += listOf(
        "-Xallocator=mimalloc", "-Xruntime-logs=gc=info", "-Xexport-kdoc"
    )
    binaries {
        /*all {
            binaryOptions["memoryModel"] = "experimental"
            binaryOptions["freezing"] = "disabled"
        }*/
    }
}