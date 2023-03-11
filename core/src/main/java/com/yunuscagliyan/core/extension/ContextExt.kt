package com.yunuscagliyan.core.extension

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

fun Context.restartApp() {
    val packageManager: PackageManager = this.packageManager
    val intent: Intent? = packageManager.getLaunchIntentForPackage(this.packageName)
    val componentName: ComponentName? = intent?.component
    componentName?.let {
        val restartIntent: Intent = Intent.makeRestartActivityTask(it)
        restartIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        this.startActivity(restartIntent)
    }
}