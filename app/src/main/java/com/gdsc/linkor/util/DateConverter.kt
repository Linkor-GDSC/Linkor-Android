package com.gdsc.linkor.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DateConverter {
    fun convertDateToString(input: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val inputDate = inputFormat.parse(input)
            outputFormat.format(inputDate)
        } catch (e: Exception) {
            null
        }
    }

}