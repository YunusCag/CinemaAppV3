package com.yunuscagliyan.core.data.enums

enum class VideoType(
    val type: String
) {
    Teaser("Teaser"),
    Featurette("Featurette"),
    Trailer("Trailer"),
    BehindTheScenes("Behind the Scenes");

    companion object {
        fun fromType(type: String) = values().find { it.type == type }
    }
}