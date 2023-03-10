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
import android.content.Intent
import android.os.Parcelable
import android.os.SystemClock
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.parcelize.Parcelize
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

/**
 * Android unit test
 * @author yaqiao
 */

@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class MMKVKotlinTestAndroid {

    private lateinit var mmkvTest: MMKVKotlinTest

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        initialize(context)
        mmkvTest = MMKVKotlinTest().apply {
            setUp()
        }
    }

    @After
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
        testStringSet()
        testULong()
    }

    @Parcelize
    private data class TestParcelable(
        val id: Int,
        val name: String,
        val height: Float
    ) : Parcelable

    @Test
    fun testParcelable() = with(mmkvTest) {
        val androidMMKV = mmkv as? MMKVImpl ?: throw IllegalStateException("MMKV type has some problems")
        val testParcelable0 = TestParcelable(
            id = 1,
            name = "Tom",
            height = 176.5f,
        )

        val result = androidMMKV.set("Parcelable", testParcelable0)
        assertEquals(result, true)

        val value0 = androidMMKV.takeParcelable("Parcelable", null, TestParcelable::class)
        assertEquals(value0, testParcelable0)

        val value1 = androidMMKV.takeParcelable(MMKVKotlinTest.KEY_NOT_EXIST, null, TestParcelable::class)
        assertEquals(value1, null)

        val testParcelable1 = TestParcelable(
            id = 2,
            name = "Jerry",
            height = 180.0f,
        )
        val value2 = androidMMKV.takeParcelable(MMKVKotlinTest.KEY_NOT_EXIST, testParcelable1, TestParcelable::class)
        assertEquals(value2, testParcelable1)
    }

    @Test
    fun testIPCUpdateInt() {
        val mmkv = mmkvWithID(MMKVTestService.SharedMMKVID, MMKVMode.MULTI_PROCESS)
        mmkv.set(MMKVTestService.SharedMMKVKey, 1024)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, MMKVTestService::class.java)
        intent.putExtra(MMKVTestService.CMD_Key, MMKVTestService.CMD_Update)
        context.startService(intent)

        SystemClock.sleep(1000 * 3)
        val intValue = mmkv.takeInt(MMKVTestService.SharedMMKVKey)
        assertEquals(intValue, 1024 + 1)
    }

    @Test
    fun testIPCLock() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, MMKVTestService::class.java)
        intent.putExtra(MMKVTestService.CMD_Key, MMKVTestService.CMD_Lock)
        context.startService(intent)

        SystemClock.sleep(1000 * 3)
        val mmkv = mmkvWithID(MMKVTestService.SharedMMKVID, MMKVMode.MULTI_PROCESS) as? MMKVImpl ?: throw IllegalStateException("MMKV type has some problems")
        val result0 = mmkv.tryLock()
        assertEquals(result0, false)

        intent.putExtra(MMKVTestService.CMD_Key, MMKVTestService.CMD_Kill)
        context.startService(intent)
        SystemClock.sleep(1000 * 3)
        val result1 = mmkv.tryLock()
        assertEquals(result1, true)
    }
}