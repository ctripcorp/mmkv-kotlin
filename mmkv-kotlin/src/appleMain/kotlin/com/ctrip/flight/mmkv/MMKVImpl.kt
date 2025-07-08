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
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSSet

/**
 * MMKV class iOS actual
 * @author yaqiao
 */

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalForeignApi::class)
class MMKVImpl internal constructor(internal val platformMMKV: MMKV) : MMKV_KMP {

    /**
     * Write value
     */

    override operator fun set(key: String, value: Int): Boolean = platformMMKV.setInt32(value, key)

    override operator fun set(key: String, value: Boolean): Boolean = platformMMKV.setBool(value, key)

    override operator fun set(key: String, value: String): Boolean = platformMMKV.setString(value, key)

    override operator fun set(key: String, value: Long): Boolean = platformMMKV.setInt64(value, key)

    override operator fun set(key: String, value: Float): Boolean = platformMMKV.setFloat(value, key)

    override operator fun set(key: String, value: Double): Boolean = platformMMKV.setDouble(value, key)

    override operator fun set(key: String, value: ByteArray): Boolean = platformMMKV.setData(value.toNSData(), key)

    override operator fun set(key: String, value: UInt): Boolean = platformMMKV.setUInt32(value, key)

    override operator fun set(key: String, value: ULong): Boolean = platformMMKV.setUInt64(value, key)

    override operator fun set(key: String, value: Set<String>?): Boolean = platformMMKV.setObject(value as? NSSet, key)

    /**
     * Read value
     */

    @Deprecated(
        message = "Renamed to 'getString' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getString(key, default)")
    )
    override fun takeString(key: String, default: String): String = platformMMKV.getStringForKey(key, default) ?: default

    @Deprecated(
        message = "Renamed to 'getBoolean' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getBoolean(key, default)")
    )
    override fun takeBoolean(key: String, default: Boolean): Boolean = platformMMKV.getBoolForKey(key, default)

    @Deprecated(
        message = "Renamed to 'getInt' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getInt(key, default)")
    )
    override fun takeInt(key: String, default: Int): Int = platformMMKV.getInt32ForKey(key, default)

    @Deprecated(
        message = "Renamed to 'getLong' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getLong(key, default)")
    )
    override fun takeLong(key: String, default: Long): Long = platformMMKV.getInt64ForKey(key, default)

    @Deprecated(
        message = "Renamed to 'getFloat' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getFloat(key, default)")
    )
    override fun takeFloat(key: String, default: Float): Float = platformMMKV.getFloatForKey(key, default)

    @Deprecated(
        message = "Renamed to 'getDouble' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getDouble(key, default)")
    )
    override fun takeDouble(key: String, default: Double): Double = platformMMKV.getDoubleForKey(key, default)

    @Deprecated(
        message = "Renamed to 'getByteArray' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getByteArray(key, default)")
    )
    override fun takeByteArray(key: String, default: ByteArray?): ByteArray? = platformMMKV.getDataForKey(key, default?.toNSData())?.toByteArray()

    @Deprecated(
        message = "Renamed to 'getUInt' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getUInt(key, default)")
    )
    override fun takeUInt(key: String, default: UInt): UInt = platformMMKV.getUInt32ForKey(key, default)

    @Deprecated(
        message = "Renamed to 'getULong' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getULong(key, default)")
    )
    override fun takeULong(key: String, default: ULong): ULong = platformMMKV.getUInt64ForKey(key, default)

    @OptIn(BetaInteropApi::class)
    @Deprecated(
        message = "Renamed to 'getStringSet' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getStringSet(key, default)")
    )
    override fun takeStringSet(key: String, default: Set<String>?): Set<String>? = platformMMKV.getObjectOfClass(NSSet.`class`()!!, key) as? Set<String> ?: default

    override fun getString(key: String, default: String): String = platformMMKV.getStringForKey(key, default) ?: default

    override fun getBoolean(key: String, default: Boolean): Boolean = platformMMKV.getBoolForKey(key, default)

    override fun getInt(key: String, default: Int): Int = platformMMKV.getInt32ForKey(key, default)

    override fun getLong(key: String, default: Long): Long = platformMMKV.getInt64ForKey(key, default)

    override fun getFloat(key: String, default: Float): Float = platformMMKV.getFloatForKey(key, default)

    override fun getDouble(key: String, default: Double): Double = platformMMKV.getDoubleForKey(key, default)

    override fun getByteArray(key: String, default: ByteArray?): ByteArray? = platformMMKV.getDataForKey(key, default?.toNSData())?.toByteArray()

    override fun getUInt(key: String, default: UInt): UInt = platformMMKV.getUInt32ForKey(key, default)

    override fun getULong(key: String, default: ULong): ULong = platformMMKV.getUInt64ForKey(key, default)

    @OptIn(BetaInteropApi::class)
    override fun getStringSet(key: String, default: Set<String>?): Set<String>? = platformMMKV.getObjectOfClass(NSSet.`class`()!!, key) as? Set<String> ?: default

    /**
     * Remove value
     */

    override fun removeValueForKey(key: String) = platformMMKV.removeValueForKey(key)

    override fun removeValuesForKeys(keys: List<String>) = platformMMKV.removeValuesForKeys(keys)

    /**
     * Size
     */

    override val actualSize: Long
        get() = platformMMKV.actualSize().toLong()

    override val count: Long
        get() = platformMMKV.count().toLong()

    override val totalSize: Long
        get() = platformMMKV.totalSize().toLong()

    /**
     * Clear
     */

    override fun clearMemoryCache() = platformMMKV.clearMemoryCache()

    override fun clearAll() = platformMMKV.clearAll()

    /**
     * Other
     */

    override fun close() = platformMMKV.close()

    override fun allKeys(): List<String> = platformMMKV.allKeys() as? List<String> ?: listOf()

    override fun containsKey(key: String): Boolean = platformMMKV.containsKey(key)

    override fun checkReSetCryptKey(key: String?) = platformMMKV.checkReSetCryptKey(key?.encodeToByteArray()?.toNSData())

    override fun mmapID(): String = platformMMKV.mmapID()

    override fun async() = platformMMKV.async()

    override fun sync() = platformMMKV.sync()

    override fun trim() = platformMMKV.trim()
}