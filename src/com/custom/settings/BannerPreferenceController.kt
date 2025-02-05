/*
 * Copyright (C) 2025 AxionAOSP Android Project
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

package com.custom.settings

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.widget.ImageView
import android.widget.TextView 

import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieFrameInfo
import com.airbnb.lottie.value.SimpleLottieValueCallback

import androidx.preference.PreferenceScreen

import com.android.settings.R
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.widget.LayoutPreference
import com.android.settings.utils.DeviceInfoUtil
import com.android.settings.utils.LottieAnimationUtils

class BannerPreferenceController(context: Context) : AbstractPreferenceController(context) {

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)
        val bannerPreference = screen.findPreference<LayoutPreference>(KEY_ROM_BANNER)
        bannerPreference?.apply {
            findViewById<TextView>(R.id.device_name)?.text = getDeviceName()

            val storageTotal = DeviceInfoUtil.getStorageTotal(mContext)

            findViewById<TextView>(R.id.storage_info)?.text = "$storageTotal"
            findViewById<TextView>(R.id.ram_info)?.text = DeviceInfoUtil.getTotalRam()
            findViewById<TextView>(R.id.camera_info)?.text = 
                "${DeviceInfoUtil.getFrontCameraMegapixels(mContext)} / ${DeviceInfoUtil.getRearCameraMegapixels(mContext)}"
            findViewById<TextView>(R.id.display_info)?.text = DeviceInfoUtil.getScreenResolution(mContext)

            val waveView: LottieAnimationView = findViewById(R.id.wave_view)
            LottieAnimationUtils.applyAnimationColor(context, waveView)
        }
    }

    private fun getDeviceName(): String {
        return Build.MODEL.replaceFirstChar { 
            if (it.isLowerCase()) it.titlecase() else it.toString() 
        }
    }

    override fun isAvailable(): Boolean = true

    override fun getPreferenceKey(): String = KEY_ROM_BANNER

    companion object {
        private const val KEY_ROM_BANNER = "rom_banner"
    }
}
