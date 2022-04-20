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
 * MMKV class Android actual
 * @author yaqiao
 */

class MMKVImpl internal constructor(internal val platformMMKV: MMKV) : MMKV_KMP {

    /**
     * Write value
     */

    override operator fun set(key: String, value: String): Boolean = platformMMKV.encode(key, value)

    override operator fun set(key: String, value: Boolean): Boolean = platformMMKV.encode(key, value)

    override operator fun set(key: String, value: Int): Boolean = platformMMKV.encode(key, value)

    override operator fun set(key: String, value: Long): Boolean = platformMMKV.encode(key, value)

    override operator fun set(key: String, value: Float): Boolean = platformMMKV.encode(key, value)

    override operator fun set(key: String, value: Double): Boolean = platformMMKV.encode(key, value)

    override operator fun set(key: String, value: ByteArray): Boolean = platformMMKV.encode(key, value)

    override operator fun set(key: String, value: UInt): Boolean = platformMMKV.encode(key, value.toInt())

    override operator fun set(key: String, value: ULong): Boolean = platformMMKV.encode(key, value.toLong())

    override operator fun set(key: String, value: Set<String>?): Boolean = platformMMKV.encode(key, value)

    /**
     * Read value
     */

    override fun takeString(key: String, default: String): String = platformMMKV.decodeString(key, default) ?: default

    override fun takeBoolean(key: String, default: Boolean): Boolean = platformMMKV.decodeBool(key ,default)

    override fun takeInt(key: String, default: Int): Int = platformMMKV.decodeInt(key, default)

    override fun takeLong(key: String, default: Long): Long = platformMMKV.decodeLong(key, default)

    override fun takeFloat(key: String, default: Float): Float = platformMMKV.decodeFloat(key, default)

    override fun takeDouble(key: String, default: Double): Double = platformMMKV.decodeDouble(key, default)

    override fun takeByteArray(key: String, default: ByteArray?): ByteArray? = platformMMKV.decodeBytes(key, default)

    override fun takeUInt(key: String, default: UInt): UInt = platformMMKV.decodeInt(key, default.toInt()).toUInt()

    override fun takeULong(key: String, default: ULong): ULong = platformMMKV.decodeLong(key, default.toLong()).toULong()

    override fun takeStringSet(key: String, default: Set<String>?): Set<String>? = platformMMKV.decodeStringSet(key, default)

    /**
     * Remove value
     */

    override fun removeValueForKey(key: String) = platformMMKV.removeValueForKey(key)

    override fun removeValuesForKeys(keys: List<String>) = platformMMKV.removeValuesForKeys(keys.toTypedArray())

    /**
     * Size
     */

    override val actualSize: Long
        get() = platformMMKV.actualSize()

    override val count: Long
        get() = platformMMKV.count()

    override val totalSize: Long
        get() = platformMMKV.totalSize()

    /**
     * Clear
     */

    override fun clearMemoryCache() = platformMMKV.clearMemoryCache()

    override fun clearAll() = platformMMKV.clearAll()

    /**
     * Other
     */

    override fun close() = platformMMKV.close()

    override fun allKeys(): List<String> = platformMMKV.allKeys()?.toList() ?: listOf()

    override fun containsKey(key: String): Boolean = platformMMKV.containsKey(key)

    override fun checkReSetCryptKey(key: String?) = platformMMKV.checkReSetCryptKey(key)

    override fun mmapID(): String = platformMMKV.mmapID()

    override fun async() = platformMMKV.async()

    override fun sync() = platformMMKV.sync()

    override fun trim() = platformMMKV.trim()

    fun lock() = platformMMKV.lock()

    fun unLock() = platformMMKV.unlock()

    fun tryLock(): Boolean = platformMMKV.tryLock()
}