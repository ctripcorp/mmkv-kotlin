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
 * MMKV create function Android actual
 * @author yaqiao
 */

actual fun defaultMMKV(): MMKV_KMP = MMKVImpl(MMKV.defaultMMKV()!!)

actual fun defaultMMKV(cryptKey: String): MMKV_KMP = MMKVImpl(MMKV.defaultMMKV(MMKV.SINGLE_PROCESS_MODE, cryptKey)!!)

actual fun mmkvWithID(
    mmapId: String,
    mode: MMKVMode,
    cryptKey: String?,
    rootPath: String?,
): MMKV_KMP = MMKVImpl(MMKV.mmkvWithID(mmapId, mode.rawValue, cryptKey, rootPath)!!)

fun mmkvWithAchemaID(
    mmapID: String,
    fd: Int,
    metaFD: Int,
    cryptKey: String?,
): MMKV_KMP = MMKVImpl(MMKV.mmkvWithAshmemFD(
    mmapID, fd, metaFD, cryptKey,
))

/*fun backedUpMMKVWithID(
    mmapID: String,
    mode: MMKVMode,
    cryptKey: String?,
    rootPath: String,
): MMKV_KMP = MMKVImpl(MMKV.backedUpMMKVWithID(
    mmapID, mode.rawValue, cryptKey, rootPath,
))*/
