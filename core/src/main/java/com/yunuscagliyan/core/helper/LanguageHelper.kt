package com.yunuscagliyan.core.helper


import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.extension.restartApp
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageHelper @Inject constructor(
    private val preferences: Preferences
) {

    var language: LanguageType = LanguageType.EN


    fun initLocale(context: Context) {
        changeLanguage(getCurrentLanguage(), context)
    }

    fun getCurrentLanguage(): LanguageType {
        val code = preferences.languageCode
        val type = LanguageType.fromCode(code) ?: getLanguageFromLocale()
        language = type
        return type
    }

    private fun getLanguageFromLocale(): LanguageType {
        val code = Locale.getDefault().language
        return LanguageType.fromCode(code) ?: LanguageType.EN
    }

    fun changeLanguage(type: LanguageType, context: Context, restart: Boolean = false) {
        preferences.languageCode = type.code
        language = type
        val locale = Locale.forLanguageTag(type.code)
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Per-app language API; the system recreates the activity when it changes.
            val localeManager = context.getSystemService(LocaleManager::class.java)
            val locales = LocaleList.forLanguageTags(type.code)
            if (localeManager != null && localeManager.applicationLocales != locales) {
                localeManager.applicationLocales = locales
            }
        } else {
            // There is no per-app language API before Android 13 for an activity that
            // is not based on AppCompat, so the configuration is updated directly.
            @Suppress("DEPRECATION")
            context.resources.apply {
                val config = Configuration(configuration)
                config.setLocale(locale)
                updateConfiguration(config, displayMetrics)
            }
        }

        if (restart) {
            context.restartApp()
        }
    }
}