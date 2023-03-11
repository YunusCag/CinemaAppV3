package com.yunuscagliyan.core.helper

import android.content.Context
import com.yunuscagliyan.core.data.enums.RegionType
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.extension.restartApp
import javax.inject.Inject

class RegionHelper @Inject constructor(
    private val preferences: Preferences
) {
    var region: RegionType = RegionType.USA

    init {
        initRegion()
    }
    private fun initRegion() {
        val code = preferences.regionCode
        val type = RegionType.fromCode(code)
        type?.let {
            region=it
        }
    }
    fun changeRegion(type: RegionType, context: Context) {
        preferences.regionCode = type.code
        this.region = type
        context.restartApp()
    }
}