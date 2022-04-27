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

import com.tencent.mmkv.MMKV

/**
 * Other top-level function
 * @author yaqiao
 */

/*actual fun backupOneToDirectory(
    mmapID: String, dstDir: String, rootPath: String?
): Boolean = MMKV.backupOneToDirectory(mmapID, dstDir, rootPath)*/

actual fun pageSize(): Long = MMKV.pageSize().toLong()

actual fun setLogLevel(logLevel: MMKVLogLevel) = MMKV.setLogLevel(logLevel.rawValue)

actual fun version(): String = MMKV.version()

actual fun unregisterHandler() = MMKV.unregisterHandler()