package com.inconus.mealmanagement.util

import java.text.SimpleDateFormat
import java.util.Locale

fun convertDate(dateString: String): String {
    val sdf = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
    val date = sdf.parse(dateString)
    val displayFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
    return displayFormat.format(date)
}