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
import android.provider.Settings
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceScreen
import com.android.settings.R
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.widget.LayoutPreference
import com.android.settings.utils.DeviceInfoUtil
import com.android.settings.utils.LottieAnimationUtils
import com.airbnb.lottie.LottieAnimationView

class BannerPreferenceController(context: Context) : AbstractPreferenceController(context) {

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)
        val bannerPreference = screen.findPreference<LayoutPreference>(KEY_ROM_BANNER) ?: return

        val deviceNameText = bannerPreference.findViewById<TextView>(R.id.device_name)
        deviceNameText?.text = getDeviceName()

        bannerPreference.findViewById<TextView>(R.id.storage_info)?.text =
            DeviceInfoUtil.getStorageTotal(mContext)
        bannerPreference.findViewById<TextView>(R.id.maintainer_info)?.text = getMaintainerName()
        bannerPreference.findViewById<TextView>(R.id.ram_info)?.text =
            DeviceInfoUtil.getTotalRam()
        bannerPreference.findViewById<TextView>(R.id.camera_info)?.text =
            "${DeviceInfoUtil.getFrontCameraMegapixels(mContext)} / ${DeviceInfoUtil.getRearCameraMegapixels(mContext)}"
        bannerPreference.findViewById<TextView>(R.id.display_info)?.text =
            DeviceInfoUtil.getScreenResolution(mContext)

        val waveView: LottieAnimationView = bannerPreference.findViewById(R.id.wave_view)
        LottieAnimationUtils.applyAnimationColor(mContext, waveView)

        bannerPreference.findViewById<android.view.View>(R.id.device_name_card)?.setOnClickListener {
            showEditDeviceNameDialog(deviceNameText)
        }
    }
    
    private fun getMaintainerName(): String {
        return android.os.SystemProperties.get("persist.sys.axion_maintainer", "Unknown")
    }

    private fun getDeviceName(): String {
        return Settings.Global.getString(mContext.contentResolver, Settings.Global.DEVICE_NAME)
            ?: android.os.Build.MODEL
    }

    private fun showEditDeviceNameDialog(deviceNameText: TextView?) {
        val editText = EditText(mContext).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            setText(getDeviceName())
        }

        AlertDialog.Builder(mContext)
            .setTitle(R.string.edit_device_name_title)
            .setView(editText)
            .setPositiveButton(R.string.ok) { _, _ ->
                val newName = editText.text.toString().trim()
                if (newName.isNotEmpty()) {
                    Settings.Global.putString(mContext.contentResolver, Settings.Global.DEVICE_NAME, newName)
                    deviceNameText?.text = newName
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    override fun isAvailable(): Boolean = true

    override fun getPreferenceKey(): String = KEY_ROM_BANNER

    companion object {
        private const val KEY_ROM_BANNER = "rom_banner"
    }
}
