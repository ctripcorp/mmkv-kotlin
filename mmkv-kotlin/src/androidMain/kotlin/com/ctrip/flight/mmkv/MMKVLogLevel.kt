/*
 * Copyright (C) 2022 Ctrip.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ctrip.flight.mmkv

/**
 * Log level Android actual
 * @author yaqiao
 */

typealias TencentMMKVLogLevel = com.tencent.mmkv.MMKVLogLevel

actual enum class MMKVLogLevel {
    LevelDebug {
        override val rawValue: TencentMMKVLogLevel = TencentMMKVLogLevel.LevelDebug
    },
    LevelInfo {
        override val rawValue: TencentMMKVLogLevel = TencentMMKVLogLevel.LevelInfo
    },
    LevelWarning {
        override val rawValue: TencentMMKVLogLevel = TencentMMKVLogLevel.LevelWarning
    },
    LevelError {
        override val rawValue: TencentMMKVLogLevel = TencentMMKVLogLevel.LevelError
    },
    LevelNone {
        override val rawValue: TencentMMKVLogLevel = TencentMMKVLogLevel.LevelNone
    };

    abstract val rawValue: TencentMMKVLogLevel
}