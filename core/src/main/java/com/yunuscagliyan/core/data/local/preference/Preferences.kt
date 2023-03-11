package com.yunuscagliyan.core.data.local.preference

interface Preferences {
    var shouldShowOnBoard: Boolean
    var languageCode: String?
    var regionCode:String

    companion object {
        const val KEY_SHOULD_SHOW_ON_BOARDING = "should_show_on_boarding"
        const val KEY_LANGUAGE_CODE = "language_code"
        const val KEY_REGION_CODE = "region_code"
    }
}