/*
 * Copyright (C) 2019 The PixelExperience Project
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

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;

import com.android.internal.util.axion.HideDeveloperStatusUtils;
import com.android.settings.core.BasePreferenceController;

import java.util.List;

public class HideDeveloperStatusPreferenceController extends BasePreferenceController {

    private static final String PREF_KEY = "hide_developer_status_settings";
    private static HideDeveloperStatusUtils hideDeveloperStatusUtils = new HideDeveloperStatusUtils();

    private UserManager userManager;
    private List<UserInfo> userInfos;

    public HideDeveloperStatusPreferenceController(Context context) {
        super(context, PREF_KEY);
        userManager = UserManager.get(context);
        userInfos = userManager.getUsers();
        for (UserInfo info: userInfos) {
            hideDeveloperStatusUtils.setApps(context, info.id);
        }
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}
