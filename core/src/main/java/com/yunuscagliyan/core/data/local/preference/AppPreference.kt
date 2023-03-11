package com.yunuscagliyan.core.data.local.preference

import android.content.SharedPreferences
import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.enums.RegionType
import com.yunuscagliyan.core.data.enums.ThemeType

class AppPreference(
    private val sharedPref: SharedPreferences
) : Preferences {
    override var shouldShowOnBoard: Boolean
        get() = sharedPref.getBoolean(Preferences.KEY_SHOULD_SHOW_ON_BOARDING, true)
        set(value) {
            sharedPref.edit()
                .putBoolean(Preferences.KEY_SHOULD_SHOW_ON_BOARDING, value)
                .apply()
        }
    override var languageCode: String?
        get() = sharedPref.getString(Preferences.KEY_LANGUAGE_CODE, null)
        set(value) {
            sharedPref.edit()
                .putString(Preferences.KEY_LANGUAGE_CODE, value)
                .apply()
        }
    override var regionCode: String
        get() = sharedPref.getString(Preferences.KEY_REGION_CODE, null)
            ?: RegionType.USA.code
        set(value) {
            sharedPref.edit()
                .putString(Preferences.KEY_REGION_CODE, value)
                .apply()
        }
    override var themeCode: String
        get() = sharedPref.getString(Preferences.KEY_THEME_CODE, null)
            ?: ThemeType.Auto.code
        set(value) {
            sharedPref.edit()
                .putString(Preferences.KEY_THEME_CODE, value)
                .apply()
        }
}