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

import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * Android MMKV initialize function
 * @author yaqiao
 */

fun initialize(context: Context): String = MMKV.initialize(context)

fun initialize(context: Context, rootDir: String): String = MMKV.initialize(context, rootDir)

fun initialize(context: Context, loader: MMKV.LibLoader): String = MMKV.initialize(context, loader)

fun initialize(context: Context, logLevel: MMKVLogLevel): String = MMKV.initialize(context, logLevel.rawValue)

fun initialize(context: Context, rootDir: String, loader: MMKV.LibLoader): String = MMKV.initialize(context, rootDir, loader)

fun initialize(context: Context, rootDir: String, logLevel: MMKVLogLevel): String = MMKV.initialize(context, rootDir, logLevel.rawValue)

fun initialize(context: Context, loader: MMKV.LibLoader, logLevel: MMKVLogLevel): String = MMKV.initialize(context, loader, logLevel.rawValue)

fun initialize(context: Context, rootDir: String, loader: MMKV.LibLoader, logLevel: MMKVLogLevel): String = MMKV.initialize(context, rootDir, loader, logLevel.rawValue)
