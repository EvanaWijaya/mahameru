package com.example.myapplication.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatDate(calendar: Calendar): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(calendar.time)
}
