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

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.Foundation.*
import platform.posix.getenv
import kotlin.experimental.ExperimentalNativeApi
import kotlin.test.*

/**
 * iOS unit test
 * @author yaqiao
 */

class MMKVKotlinTestIos {

    private lateinit var mmkvTest: MMKVKotlinTest

    @BeforeTest
    @OptIn(ExperimentalForeignApi::class)
    fun setUp() {
        initialize("/Users/${getenv("USER")!!.toKString()}/Downloads")
        mmkvTest = MMKVKotlinTest().apply {
            setUp()
        }
    }

    @AfterTest
    fun setDown() {
        mmkvTest.testDown()
    }

    @Test
    fun testCommon() = with(mmkvTest) {
        testBoolean()
        testInt()
        testLong()
        testFloat()
        testDouble()
        testString()
        testByteArray()
        testUInt()
        testULong()
        testStringSet()
        testRemove()
    }

    @Test
    fun testNSDate() {
        val mmkvImpl = mmkvTest.mmkv as? MMKVImpl ?: throw IllegalStateException("MMKV type has some problems")
        val date = NSDate()
        val result = mmkvImpl.set("NSDate", date)
        assertEquals(result, true)

        val value0 = mmkvImpl.getNSDate("NSDate")
        assertEquals(value0?.timeIntervalSince1970, date.timeIntervalSince1970)

        val value1 = mmkvImpl.getNSDate(MMKVKotlinTest.KEY_NOT_EXIST)
        assertEquals(value1, null)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    @OptIn(BetaInteropApi::class)
    fun testNSObject() {
        val mmkvImpl = mmkvTest.mmkv as? MMKVImpl ?: throw IllegalStateException("MMKV type has some problems")
        val list = listOf("Aa", "Bb", "Cc")
        
        val result = mmkvImpl.set("NSObject", list as? NSArray)
        assertEquals(result, true)

        val value0 = mmkvImpl.getObject("NSObject", NSArray.`class`()!!)
        assertEquals(value0 as? List<String>, list)

        val value1 = mmkvImpl.getObject(MMKVKotlinTest.KEY_NOT_EXIST, NSArray.`class`()!!)
        assertEquals(value1, null)
    }

    @Test
    @OptIn(ExperimentalNativeApi::class)
    fun emptyNsDataToByteArray() {
        @Suppress("CAST_NEVER_SUCCEEDS")
        val data = ("" as NSString).dataUsingEncoding(NSUTF8StringEncoding) as NSData
        val bytes = data.toByteArray()
        assert(bytes.isEmpty())
    }
}