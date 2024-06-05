package com.inconus.mealmanagement.util

import androidx.compose.ui.res.stringResource
import com.inconus.mealmanagement.R
import java.text.SimpleDateFormat
import java.util.Locale
fun convertDate(dateLong: Long): String {
    val dateString = dateLong.toString()
    try {
        // 날짜 형식을 yyyyMMdd로 설정
        val parser = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
        val date = parser.parse(dateString) // 날짜 파싱
        val displayFormat = SimpleDateFormat("dd", Locale.KOREA)
        return displayFormat.format(date) // 변환된 형식으로 출력
    } catch (e: Exception) {
        // 파싱 오류가 발생하면 콘솔에 로그를 출력하고 오류 메시지 반환
        e.printStackTrace()
        return "날짜 변환 오류"
    }
}