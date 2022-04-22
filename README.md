# MMKV for Kotlin Multiplatform

中文版本请参看[这里](README_CN.md)

MMKV for Kotlin Multiplatform is a wrapper for [MMKV](https://github.com/Tencent/MMKV) using Kotlin API, developed by Ctrip Flight mobile team. Currently only KMM (Android/iOS) is supported, It may be gradually expanded to macOS in the future.

## Tutorial

### Installation Via Maven in Gradle

Kotlin Multiplatform Common (kts):

```kotlin
dependencies {     
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin:1.0.0")
}
```

Current version based on `Kotlin 1.6.10` and `MMKV 1.2.12`.

Pure Android platform (kts):

```kotlin
dependencies {     
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin-android:1.0.0")
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


* The following types are additionally supported in the iOS source set:
  * `NSDate and any class that implements NSCoding protocol`


## Note
 
- MMKV-Kotlin currently does not support migrating old data from SharedPreferences and NSUserDefaults.

- MMKV-Kotlin is based on old Kotlin/Native memory management in iOS，We plan to migrate to Kotlin/Native's new memory management when it is released.

## License

Distributed under the [Apache License, Version 2.0](https://github.com/aws/jsii/tree/main/packages/jsii-reflect#license).

See [LICENSE](LICENSE.txt) for more information.