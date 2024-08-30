import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.maven.publish)
    signing
}

version = "1.2.14"
group = "com.ctrip.flight.mmkv"

@OptIn(ExperimentalKotlinGradlePluginApi::class)
kotlin {
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
        ios.deploymentTarget = "17.5.1"
        osx.deploymentTarget = "14.4.1"
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
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.mmkv)
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit)
                implementation(libs.androidx.test.core)
                implementation(libs.androidx.test.runner)
                implementation(libs.androidx.test.rules)
            }
        }
    }
}

android {
    namespace = "com.ctrip.flight.mmkv"
    compileSdk = 35
    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_17
    }
}

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
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

// TODO: remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
    dependsOn(project.tasks.withType(Sign::class.java))
}