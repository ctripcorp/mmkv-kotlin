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

import cocoapods.MMKV.MMKV
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData

/**
 * MMKV create function iOS actual
 * @author yaqiao
 */

private fun String.asNSDataCryptKey(): NSData = encodeToByteArray().toNSData()

@OptIn(ExperimentalForeignApi::class)
actual fun defaultMMKV(): MMKV_KMP = MMKVImpl(MMKV.defaultMMKV()!!)

@OptIn(ExperimentalForeignApi::class)
actual fun defaultMMKV(cryptKey: String): MMKV_KMP =
    MMKVImpl(MMKV.defaultMMKVWithCryptKey(cryptKey.asNSDataCryptKey())!!)

@OptIn(ExperimentalForeignApi::class)
actual fun mmkvWithID(
    mmapId: String,
    mode: MMKVMode,
    cryptKey: String?,
    rootPath: String?,
): MMKV_KMP {
    val platformMMKV = when {
        cryptKey != null && rootPath != null -> MMKV.mmkvWithID(
            mmapId,
            cryptKey.asNSDataCryptKey(),
            rootPath = rootPath,
        )
        cryptKey == null && rootPath != null -> MMKV.mmkvWithID(
            mmapId,
            rootPath = rootPath,
        )
        cryptKey != null && rootPath == null -> MMKV.mmkvWithID(
            mmapId,
            cryptKey.asNSDataCryptKey(),
        )
        cryptKey == null && rootPath == null -> MMKV.mmkvWithID(
            mmapId,
            mode = mode.rawValue.toULong(),
        )
        else -> throw IllegalStateException("Impossible situation")
    }!!
    return MMKVImpl(platformMMKV)
}