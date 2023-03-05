package com.yunuscagliyan.core.helper


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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

    fun changeLanguage(type: LanguageType, context: Context, restart: Boolean = false) {
        preferences.languageCode = type.code
        language = type
        val locale = Locale(type.code)

        context.resources.apply {
            val config = Configuration(configuration)
            context.createConfigurationContext(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, displayMetrics)
            if (restart) {
                //(context as? Activity?)?.recreate()
                restartApp(context)
            }
        }
    }

    private fun restartApp(context: Context) {
        val packageManager: PackageManager = context.packageManager
        val intent: Intent? = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName: ComponentName? = intent?.component
        componentName?.let {
            val restartIntent: Intent = Intent.makeRestartActivityTask(it)
            restartIntent.flags=
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(restartIntent)
        }
    }

}