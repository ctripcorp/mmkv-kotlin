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

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process

/**
 * MMKV multi processes tests Service
 * @author yaqiao
 */

class MMKVTestService : Service() {

    companion object {
        const val SharedMMKVID = "SharedMMKVID"
        const val SharedMMKVKey = "SharedMMKVKey"

        const val CMD_Key = "CMD_Key"
        const val CMD_Update = "CMD_Update"
        const val CMD_Lock = "CMD_Lock"
        const val CMD_Kill = "CMD_Kill"
    }

    override fun onCreate() {
        super.onCreate()
        initialize(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val mmkv = mmkvWithID(SharedMMKVID, MMKVMode.MULTI_PROCESS) as? MMKVImpl ?: throw IllegalStateException("MMKV type has some problems")
        val cmd = intent.getStringExtra(CMD_Key)
        when (cmd) {
            CMD_Update -> {
                val value = mmkv.takeInt(SharedMMKVKey)
                mmkv[SharedMMKVKey] = value + 1
            }
            CMD_Lock -> mmkv.lock()
            CMD_Kill -> stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Process.killProcess(Process.myPid())
    }

    override fun onBind(intent: Intent?): IBinder? = null

}