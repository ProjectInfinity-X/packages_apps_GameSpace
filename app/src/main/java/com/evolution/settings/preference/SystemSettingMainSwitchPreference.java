/*
 * Copyright (C) 2019-2022 Evolution X
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evolution.settings.preference;

import android.content.Context;
import android.provider.Settings;
import android.os.UserHandle;
import android.util.AttributeSet;

import com.android.settingslib.widget.MainSwitchPreference;

import com.evolution.settings.preference.SystemSettingsStore;

public class SystemSettingMainSwitchPreference extends MainSwitchPreference {

    public SystemSettingMainSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPreferenceDataStore(new SystemSettingsStore(context.getContentResolver()));
    }

    public SystemSettingMainSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPreferenceDataStore(new SystemSettingsStore(context.getContentResolver()));
    }

    public SystemSettingMainSwitchPreference(Context context) {
        super(context);
        setPreferenceDataStore(new SystemSettingsStore(context.getContentResolver()));
    }
}
