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
 * MMKV common source set expect class
 * @author yaqiao
 */

interface MMKV_KMP {

    /**
     * Write value
     */

    operator fun set(key: String, value: String): Boolean

    operator fun set(key: String, value: Boolean): Boolean

    operator fun set(key: String, value: Int): Boolean

    operator fun set(key: String, value: Long): Boolean

    operator fun set(key: String, value: Float): Boolean

    operator fun set(key: String, value: Double): Boolean

    operator fun set(key: String, value: ByteArray): Boolean

    operator fun set(key: String, value: UInt): Boolean

    operator fun set(key: String, value: ULong): Boolean

    operator fun set(key: String, value: Set<String>?): Boolean

    /**
     * Read value
     */
    @Deprecated(
        message = "Renamed to 'getString' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getString(key, default)")
    )
    fun takeString(key: String, default: String = ""): String

    @Deprecated(
        message = "Renamed to 'getBoolean' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getBoolean(key, default)")
    )
    fun takeBoolean(key: String, default: Boolean = false): Boolean

    @Deprecated(
        message = "Renamed to 'getInt' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getInt(key, default)")
    )
    fun takeInt(key: String, default: Int = 0): Int

    @Deprecated(
        message = "Renamed to 'getLong' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getLong(key, default)")
    )
    fun takeLong(key: String, default: Long = 0): Long

    @Deprecated(
        message = "Renamed to 'getFloat' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getFloat(key, default)")
    )
    fun takeFloat(key: String, default: Float = 0f): Float

    @Deprecated(
        message = "Renamed to 'getDouble' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getDouble(key, default)")
    )
    fun takeDouble(key: String, default: Double = 0.0): Double

    @Deprecated(
        message = "Renamed to 'getByteArray' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getByteArray(key, default)")
    )
    fun takeByteArray(key: String, default: ByteArray? = null): ByteArray?

    @Deprecated(
        message = "Renamed to 'getUInt' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getUInt(key, default)")
    )
    fun takeUInt(key: String, default: UInt = 0u): UInt

    @Deprecated(
        message = "Renamed to 'getULong' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getULong(key, default)")
    )
    fun takeULong(key: String, default: ULong = 0u): ULong

    @Deprecated(
        message = "Renamed to 'getStringSet' for clarity, as the 'take' prefix could be confusing.",
        replaceWith = ReplaceWith("getStringSet(key, default)")
    )
    fun takeStringSet(key: String, default: Set<String>? = null): Set<String>?

    fun getString(key: String, default: String = ""): String

    fun getBoolean(key: String, default: Boolean = false): Boolean

    fun getInt(key: String, default: Int = 0): Int

    fun getLong(key: String, default: Long = 0): Long

    fun getFloat(key: String, default: Float = 0f): Float

    fun getDouble(key: String, default: Double = 0.0): Double

    fun getByteArray(key: String, default: ByteArray? = null): ByteArray?

    fun getUInt(key: String, default: UInt = 0u): UInt

    fun getULong(key: String, default: ULong = 0u): ULong

    fun getStringSet(key: String, default: Set<String>? = null): Set<String>?

    /**
     * Remove value
     */

    fun removeValueForKey(key: String)

    fun removeValuesForKeys(keys: List<String>)

    /**
     * Size
     */

    val actualSize: Long

    val count: Long

    val totalSize: Long

    /**
     * Clear
     */

    fun clearMemoryCache()

    fun clearAll()

    /**
     * Other
     */

    fun close()

    fun allKeys(): List<String>

    fun containsKey(key: String): Boolean

    fun checkReSetCryptKey(key: String?)

    fun mmapID(): String

    fun async()

    fun sync()

    fun trim()
}