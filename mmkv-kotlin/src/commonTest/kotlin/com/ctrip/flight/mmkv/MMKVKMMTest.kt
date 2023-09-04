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

import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/**
 * MMKV_Kotlin core unit test
 * @author yaqiao
 */

class MMKVKotlinTest {

    companion object {
        const val KEY_NOT_EXIST = "Key_Not_Exist"
    }

    lateinit var mmkv: MMKV_KMP
        private set

    fun setUp() {
        mmkv = mmkvWithID("unitTest", cryptKey = "UnitTestCryptKey")
    }

    fun testDown() {
        mmkv.clearAll()
    }

    fun testBoolean() {
        val result = mmkv.set("Boolean", true)
        assertEquals(true, result)
        val value0 = mmkv.takeBoolean("Boolean")
        assertEquals(true, value0)
        val value1 = mmkv.takeBoolean(KEY_NOT_EXIST)
        assertEquals(false, value1)
        val value2 = mmkv.takeBoolean(KEY_NOT_EXIST, true)
        assertEquals(true, value2)
    }

    fun testInt() {
        val result = mmkv.set("Int", Int.MAX_VALUE)
        assertEquals(true, result)
        val value0 = mmkv.takeInt("Int")
        assertEquals(Int.MAX_VALUE, value0)
        val value1 = mmkv.takeInt(KEY_NOT_EXIST)
        assertEquals(0, value1)
        val value2 = mmkv.takeInt(KEY_NOT_EXIST, -1)
        assertEquals(-1, value2)
    }

    fun testLong() {
        val result = mmkv.set("Long", Long.MAX_VALUE)
        assertEquals(true, result)
        val value0 = mmkv.takeLong("Long")
        assertEquals(Long.MAX_VALUE, value0)
        val value1 = mmkv.takeLong(KEY_NOT_EXIST)
        assertEquals(0, value1)
        val value2 = mmkv.takeLong(KEY_NOT_EXIST, -1)
        assertEquals(-1, value2)
    }

    fun testFloat() {
        val result = mmkv.set("Float", Float.MAX_VALUE)
        assertEquals(true, result)
        val value0 = mmkv.takeFloat("Float")
        assertEquals(Float.MAX_VALUE, value0)
        val value1 = mmkv.takeFloat(KEY_NOT_EXIST)
        assertEquals(0f, value1)
        val value2 = mmkv.takeFloat(KEY_NOT_EXIST, -1f)
        assertEquals(-1f, value2)
    }

    fun testDouble() {
        val result = mmkv.set("Double", Double.MAX_VALUE)
        assertEquals(true, result)
        val value0 = mmkv.takeDouble("Double")
        assertEquals(Double.MAX_VALUE, value0)
        val value1 = mmkv.takeDouble(KEY_NOT_EXIST)
        assertEquals(0.0, value1)
        val value2 = mmkv.takeDouble(KEY_NOT_EXIST, -1.0)
        assertEquals(-1.0, value2)
    }

    fun testString() {
        val str = "Hello MMKV with Kotlin"
        val result = mmkv.set("String", str)
        assertEquals(true, result)
        val value0 = mmkv.takeString("String")
        assertEquals(str, value0)
        val value1 = mmkv.takeString(KEY_NOT_EXIST)
        assertEquals("", value1)
        val emptyStr = "Empty"
        val value2 = mmkv.takeString(KEY_NOT_EXIST, emptyStr)
        assertEquals(emptyStr, value2)
    }

    fun testByteArray() {
        val byteArray = byteArrayOf(1, 0, 0, 8, 6)
        val result = mmkv.set("ByteArray", byteArray)
        assertEquals(true, result)
        val value0 = mmkv.takeByteArray("ByteArray")
        assertContentEquals(byteArray, value0)
        val value1 = mmkv.takeByteArray(KEY_NOT_EXIST)
        assertContentEquals(null, value1)
    }

    fun testUInt() {
        val result = mmkv.set("UInt", UInt.MAX_VALUE)
        assertEquals(true, result)
        val value0 = mmkv.takeUInt("UInt")
        assertEquals(UInt.MAX_VALUE, value0)
        val value1 = mmkv.takeUInt(KEY_NOT_EXIST)
        assertEquals(0U, value1)
        val value2 = mmkv.takeUInt(KEY_NOT_EXIST, 99U)
        assertEquals(99U, value2)
    }

    fun testULong() {
        val result = mmkv.set("ULong", ULong.MAX_VALUE)
        assertEquals(true, result)
        val value0 = mmkv.takeULong("ULong")
        assertEquals(ULong.MAX_VALUE, value0)
        val value1 = mmkv.takeULong(KEY_NOT_EXIST)
        assertEquals(0UL, value1)
        val value2 = mmkv.takeULong(KEY_NOT_EXIST, 999UL)
        assertEquals(999UL, value2)
    }

    fun testRemove() {
        val byteArray = byteArrayOf(2, 0, 0 ,8, 8, 8)
        with(mmkv) {
            var result = set("Boolean_1", true)
            result = result && set("Int_1", Int.MAX_VALUE)
            result = result && set("Long_1", Long.MAX_VALUE)
            result = result && set("Float_1", Float.MAX_VALUE)
            result = result && set("Double_1", Double.MAX_VALUE)
            result = result && set("String_1", "hello")
            result = result && set("UInt_1", UInt.MAX_VALUE)
            result = result && set("ULong_1", ULong.MAX_VALUE)
            result = result && set("ByteArray_1", byteArray)
            assertEquals(true, result)
        }

        val oldCount = mmkv.count
        mmkv.removeValueForKey("Boolean_1")
        mmkv.removeValuesForKeys(listOf("Int_1", "Long_1"))
        assertEquals(mmkv.count, oldCount - 3)

        val resultB = mmkv.takeBoolean("Boolean_1", false)
        assertEquals(false, resultB)

        val resultI = mmkv.takeInt("Int_1")
        assertEquals(0, resultI)

        val resultL = mmkv.takeLong("Long_1")
        assertEquals(0L, resultL)

        val resultF = mmkv.takeFloat("Float_1")
        assertEquals(Float.MAX_VALUE, resultF)

        val resultD = mmkv.takeDouble("Double_1")
        assertEquals(Double.MAX_VALUE, resultD)

        val resultS = mmkv.takeString("String_1")
        assertEquals("hello", resultS)

        val resultUI = mmkv.takeUInt("UInt_1")
        assertEquals(UInt.MAX_VALUE, resultUI)

        val resultUL = mmkv.takeULong("ULong_1")
        assertEquals(ULong.MAX_VALUE, resultUL)

        val resultBA = mmkv.takeByteArray("ByteArray_1")
        assertContentEquals(byteArray, resultBA)
    }

    fun testStringSet() {
        val strSet0 = setOf("t", "r", "i", "p")
        val result = mmkv.set("StringSet", strSet0)
        assertEquals(true, result)

        val value0 = mmkv.takeStringSet("StringSet")
        assertEquals(strSet0, value0)

        val value1 = mmkv.takeStringSet(KEY_NOT_EXIST)
        assertEquals(null, value1)

        val value2 = mmkv.takeStringSet(KEY_NOT_EXIST)
        assertEquals(null, value2)
    }
}