import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlin-parcelize")
    id("maven-publish")
    signing
}

version = "1.2.7"
group = "com.ctrip.flight.mmkv"

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

val mmkvVersion = "1.2.16"

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
            isStatic = true
        }
        pod(
            name = "MMKV",
            version = mmkvVersion,
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
                api("com.tencent:mmkv-static:$mmkvVersion")
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                dependsOn(commonTest)
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:core:1.5.0")
                implementation("androidx.test:runner:1.5.2")
                implementation("androidx.test:rules:1.5.0")
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
    compileSdk = 33
    buildToolsVersion = "33.0.2"
    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
                val NEXUS_USERNAME: String by project
                val NEXUS_PASSWORD: String by project
                username = NEXUS_USERNAME
                password = NEXUS_PASSWORD
            }
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
        }
    }
    signing {
        val SIGNING_KEY_ID: String by project
        val SIGNING_KEY: String by project
        val SIGNING_PASSWORD: String by project
        useInMemoryPgpKeys(SIGNING_KEY_ID, SIGNING_KEY, SIGNING_PASSWORD)
        sign(publishing.publications)
    }
}

fun KotlinNativeTarget.setupNativeConfig() {
    compilations["main"].kotlinOptions.freeCompilerArgs += listOf(
        "-Xruntime-logs=gc=info", "-Xexport-kdoc"
    )
}