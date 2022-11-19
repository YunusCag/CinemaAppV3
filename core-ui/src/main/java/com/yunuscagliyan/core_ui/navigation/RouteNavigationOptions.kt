package com.yunuscagliyan.core_ui.navigation

import androidx.navigation.NavOptions

data class RouteNavigationOptions(
    val popUpTo: String,
    val inclusive: Boolean = false
){
    fun getNavOptions() = NavOptions.Builder().setPopUpTo(popUpTo, inclusive).build()
}
