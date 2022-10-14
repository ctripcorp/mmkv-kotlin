# MMKV for Kotlin Multiplatform

中文版本请参看[这里](README_CN.md)

MMKV for Kotlin Multiplatform is a wrapper for [MMKV](https://github.com/Tencent/MMKV) using Kotlin API, developed by Ctrip Flight mobile team. Currently Android/iOS/macOS are supported.

## Tutorial

### Installation Via Maven in Gradle

Kotlin Multiplatform Common (kts):

```kotlin
dependencies {     
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin:1.2.4")
}
```

Current version based on `Kotlin 1.7.10` and `MMKV 1.2.14`.

Pure Android platform (kts):

```kotlin
dependencies {     
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin-android:1.2.4")
}
```

Kotlin/Native on macOS：

```kotlin
dependencies { 
    // Intel Chip
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin-macosx64:1.2.4")
    
    // M1&M2 Chip
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin-macosarm64:1.2.4")
}
```
Note, if you want to import MMKV-Kotlin to your Kotlin/Native executable project that target is macOS, you need to manually add dependency on MMKV, and add `linkerOpts` on MMKV and MMKVCore：

```kotlin
kotlin {
    macosX64 {
        binaries {
            // ......
            all {
                val moduleName = "mmkv_operator"
                val mmkvPath = "${buildDir.absolutePath}/cocoapods/synthetic/OSX/$moduleName/build/Release/MMKV"
                val mmkvCorePath = "${buildDir.absolutePath}/cocoapods/synthetic/OSX/$moduleName//build/Release/MMKVCore"
                linkerOpts += listOf(
                    "-F$mmkvPath",
                    "-rpath", mmkvPath,
                    "-framework", "MMKV",
                    "-F$mmkvCorePath",
                    "-rpath", mmkvCorePath,
                    "-framework", "MMKVCore"
                )
            }
        }
    }
    cocoapods {
        // ......
        pod(name = "MMKV") {
            version = "1.2.14"
            moduleName = "MMKV"
        }
    }
    // ......
}
```

### Initialization and Configure Root Path

You can initialize MMKV when app or process initialization. [MMKV-Android](https://github.com/Tencent/MMKV/tree/master/Android/MMKV) initialization API depends on [Context](https://developer.android.com/reference/android/content/Context). So, Android's initialization API is different from iOS.

Android:

```kotlin
import com.ctrip.flight.mmkv.initialize

// In Android source set
fun initializeMMKV(context: Context) {
    val rootDir = initialize(context)
    Log.d("MMKV Path", rootDir)
}
```

iOS:

```kotlin
import com.ctrip.flight.mmkv.initialize

// In iOS source set
fun initializeMMKV(rootDir: String) {
    initialize(rootDir)
    Log.d("MMKV Path", rootDir)
}
```

You also could call [MMKV-Android](https://github.com/Tencent/MMKV/tree/master/Android/MMKV) (Java) or [MMKV-iOS](https://github.com/Tencent/MMKV/tree/master/iOS) (Objective-C) initialization API in your Android or iOS app project.

### CRUD Operations

- MMKV has a **global instance**, you can use it directly:：

```kotlin
import com.ctrip.flight.mmkv.defaultMMKV

fun demo() {
    val kv = defaultMMKV()

    kv.set("Boolean", true)
    println("Boolean: ${kv.takeBoolean("Boolean")}")

    kv.set("Int", Int.MIN_VALUE)
    println("Int: ${kv.takeInt("Int")}")

    kv.set("Long", Long.MAX_VALUE)
    println("Long: ${kv.takeLong("Long")}")

    kv.set("Float", -3.14f)
    println("Float: ${kv.takeFloat("Float")}")

    kv.set("Double", Double.MIN_VALUE)
    println("Double: ${kv.takeDouble("Double")}")

    kv.set("String", "Hello from mmkv")
    println("String: ${kv.takeString("String")}")

    val bytes = byteArrayOf(
        'm'.code.toByte(), 
        'm'.code.toByte(), 
        'k'.code.toByte(), 
        'v'.code.toByte(),
    )
    kv.set("ByteArray", bytes)
    println("ByteArray: ${kv.takeByteArray("ByteArray")?.toString()}")
}
```

- **Deleting & Querying:**

```kotlin
kv.removeValueForKey("Boolean")
println("Boolean: ${kv.takeBoolean("Boolean")}")

kv.removeValuesForKeys(listOf("Int", "Long"))
println("allKeys: ${kv.allKeys()}")

val hasBoolean = kv.containsKey("Boolean")
```

- If different modules/logics need **isolated storage**, you can also create your own MMKV instance separately:

```kotlin
import com.ctrip.flight.mmkv.mmkvWithID
...
val kvWithMyId = mmkvWithID("MyID")
kvWithMyId.set("Boolean", true)
```

- If **multi-process accessing** is needed, you can set `MMKV.MULTI_PROCESS_MODE` on MMKV initialization:

```kotlin
import com.ctrip.flight.mmkv.mmkvWithID
import com.ctrip.flight.mmkv.MMKVModel

...
val kvMultiProcess = mmkvWithID("InterProcessKV", MMKVModel.MULTI_PROCESS)
kvMultiProcess.set("Boolean", true)
```

## Supported Types

* Supported Kotlin types in Kotlin Multiplatform common source set：
  * `Boolean、Int、Long、Float、Double、String、UInt、ULong、ByteArray、Set<String>`
 

* The following types are additionally supported in the Android source set:
  * `Any class that implements Parcelable`


* The following types are additionally supported in the Apple source set:
  * `NSDate and any class that implements NSCoding protocol`


## Note
 
- MMKV-Kotlin currently does not support migrating old data from SharedPreferences and NSUserDefaults.

## License

Distributed under the [Apache License, Version 2.0](https://github.com/aws/jsii/tree/main/packages/jsii-reflect#license).

See [LICENSE](LICENSE.txt) for more information.