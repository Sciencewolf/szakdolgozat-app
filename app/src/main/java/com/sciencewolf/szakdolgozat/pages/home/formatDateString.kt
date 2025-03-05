package com.sciencewolf.szakdolgozat.pages.home

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDateString(dateString: String, pattern: String = "yyyy MMMM dd"): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}