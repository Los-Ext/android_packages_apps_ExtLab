/*
 * Copyright (C) 2023 crDroid Android Project
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
package com.custom.settings.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.utils.ThemeStyleUtils;

public class Keyboard extends SettingsPreferenceFragment 
        implements OnPreferenceChangeListener {

    private static final String KEY_HIDE_IME_STYLE = "hide_ime_space_style";
    private ThemeStyleUtils themeStyleUtils;
    private ListPreference hideImePref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.keyboard_space_settings);

        Context context = getContext();
        themeStyleUtils = new ThemeStyleUtils(context);
        ContentResolver resolver = context.getContentResolver();

        hideImePref = findPreference(KEY_HIDE_IME_STYLE);
        hideImePref.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == hideImePref) {
            int value = Integer.parseInt((String) newValue);
            Settings.System.putIntForUser(
                    getContext().getContentResolver(), KEY_HIDE_IME_STYLE, value, UserHandle.USER_CURRENT);
            themeStyleUtils.updateThemeStyle(KEY_HIDE_IME_STYLE, 
                    "android.theme.customization.hide_ime_space", "android", false);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.VIEW_UNKNOWN;
    }
}
