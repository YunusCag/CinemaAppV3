package com.yunuscagliyan.core_ui.extension

import com.yunuscagliyan.core.util.Constants.DateFormatUtil.ddMMMMyyyy
import com.yunuscagliyan.core.util.Constants.DateFormatUtil.yyyyMMdd
import timber.log.Timber
import java.text.SimpleDateFormat


fun String.formatDate(
    inputFormat: String = yyyyMMdd,
    outputFormat: String = ddMMMMyyyy
): String? {
    val input = SimpleDateFormat(inputFormat)
    val output = SimpleDateFormat(outputFormat)
    return try {
        val date = input.parse(this)
        output.format(date)
    } catch (e: Exception) {
        Timber.e(e.message)
        null
    }
}