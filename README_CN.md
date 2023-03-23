# MMKV for Kotlin Multiplatform

MMKV for Kotlin Multiplatform 是对 [MMKV](https://github.com/Tencent/MMKV) 的 Kotlin API 封装，由携程机票移动端团队开发。当前支持 Android/iOS/macOS。

## 使用指南

### 在 Gradle 中使用 Maven 安装引入

Kotlin Multiplatform Common (kts):

```kotlin
dependencies { 
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin:1.2.6")
}
```

当前版本依赖于 `Kotlin 1.8.10` 以及 `MMKV 1.2.15`。

纯 Android 平台（kts）：

```kotlin
dependencies { 
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin-android:1.2.6")
}
```

Kotlin/Native on macOS：

```kotlin
dependencies { 
    // Intel 芯片
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin-macosx64:1.2.6")
    
    // M1&M2 芯片
    implementation("com.ctrip.flight.mmkv:mmkv-kotlin-macosarm64:1.2.6")
}
```
注意，如果你的工程为 macOS 的 Kotlin/Native 可执行程序工程，或者它直接向一个 iOS 应用程序工程提供 framework，那么您需要手动在工程中添加对 MMKV 的依赖，并可能需要添加对 MMKV 及 MMKVCore 的 `linkerOpts`：

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
            version = "1.2.15"
            moduleName = "MMKV"
        }
    }
    // ......
}
```

### 初始化与配置根目录

可在 App 或进程启动时初始化 MMKV。由于 [MMKV-Android](https://github.com/Tencent/MMKV/tree/master/Android/MMKV) 的初始化 API 依赖 [Context](https://developer.android.com/reference/android/content/Context)，因此 Android 与 iOS 平台初始化 API 有所不同。

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

当然，您也可以在您的 Android 或 iOS app 工程中调用 [MMKV-Android](https://github.com/Tencent/MMKV/tree/master/Android/MMKV)（Java）或 [MMKV-iOS](https://github.com/Tencent/MMKV/tree/master/iOS)（Objective-C）的初始化 API 完成初始化。

### CRUD 操作

- MMKV 提供一个**全局的实例**，可以直接使用：

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

- **删除 & 查询：**

```kotlin
kv.removeValueForKey("Boolean")
println("Boolean: ${kv.takeBoolean("Boolean")}")

kv.removeValuesForKeys(listOf("Int", "Long"))
println("allKeys: ${kv.allKeys()}")

val hasBoolean = kv.containsKey("Boolean")
```

- 如果不同业务需要**区别存储**，也可以单独创建自己的实例：

```kotlin
import com.ctrip.flight.mmkv.mmkvWithID
...
val kvWithMyId = mmkvWithID("MyID")
kvWithMyId.set("Boolean", true)
```

- 如果业务需要**多进程访问**，那么在初始化的时候加上标志位 `MMKV.MULTI_PROCESS_MODE`：

```kotlin
import com.ctrip.flight.mmkv.mmkvWithID
import com.ctrip.flight.mmkv.MMKVModel

...
val kvMultiProcess = mmkvWithID("InterProcessKV", MMKVModel.MULTI_PROCESS)
kvMultiProcess.set("Boolean", true)
```

## 支持的数据类型

* 在 Kotlin Multiplatform common source set 中支持以下 Kotlin 类型：
  * `Boolean、Int、Long、Float、Double、String、UInt、ULong、ByteArray、Set<String>`
 

* 在 Android source set 中额外支持以下类型：
  * `Parcelable 的实现者`


* 在 Apple source set 中额外支持以下类型：
  * `NSDate 以及 NSCoding 协议的实现者`


## 注意
 
- MMKV-Kotlin 暂时不支持从 SharedPreferences 与 NSUserDefaults 中迁移旧数据。

## 开源许可

本项目于 [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) 协议下开源。

查看 [LICENSE](LICENSE.txt) 获取更多信息。