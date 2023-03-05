package com.yunuscagliyan.core.helper


import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import com.yunuscagliyan.core.data.enums.LanguageType
import com.yunuscagliyan.core.data.local.preference.Preferences
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

    fun changeLanguage(type: LanguageType, context: Context, reCreate: Boolean = false) {
        val locale = Locale(type.code)
        preferences.languageCode = type.code
        language = type

        context.resources.apply {
            val config = Configuration(configuration)
            context.createConfigurationContext(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, displayMetrics)
            if (reCreate) {
                (context as? Activity?)?.recreate()
            }
        }
    }

}