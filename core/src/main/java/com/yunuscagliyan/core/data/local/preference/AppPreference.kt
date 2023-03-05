package com.yunuscagliyan.core.data.local.preference

import android.content.SharedPreferences
import com.yunuscagliyan.core.data.enums.LanguageType

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
        get() = sharedPref.getString(Preferences.KEY_LANGUAGE_CODE,null)
        set(value) {
            sharedPref.edit()
                .putString(Preferences.KEY_LANGUAGE_CODE, value)
                .apply()
        }
}