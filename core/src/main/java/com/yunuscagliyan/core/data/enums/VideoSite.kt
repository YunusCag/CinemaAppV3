package com.yunuscagliyan.core.data.enums

enum class VideoSite(
    val site: String
) {
    Youtube("YouTube");

    companion object {
        fun fromSite(site: String) = values().find { it.site == site }
    }
}