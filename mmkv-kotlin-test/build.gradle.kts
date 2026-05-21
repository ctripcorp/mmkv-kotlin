plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
}

version = "1.3.2"

kotlin {
    jvmToolchain(21)
    android {
        namespace = "com.ctrip.flight.mmkv.test"
        compileSdk = 37
        minSdk = 23
        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
    iosArm64()
    iosSimulatorArm64()
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
            baseName = "MMKV-Kotlin-Test"
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
        commonMain.dependencies {
            implementation(kotlin("test"))
            implementation(project(":mmkv-kotlin"))
        }
        getByName("androidDeviceTest").dependencies {
            implementation(kotlin("test-junit"))
            implementation(libs.junit)
            implementation(libs.androidx.test.core)
            implementation(libs.androidx.test.runner)
            implementation(libs.androidx.test.rules)
        }
    }
}
