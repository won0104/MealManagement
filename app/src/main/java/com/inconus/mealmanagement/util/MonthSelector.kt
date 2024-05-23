package com.inconus.mealmanagement.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.inconus.mealmanagement.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// 월 이동
@Composable
fun MonthSelector(calendar: Calendar, onMonthChanged: (Calendar) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = {
            val newCalendar = calendar.clone() as Calendar
            newCalendar.add(Calendar.MONTH, -1)
            onMonthChanged(newCalendar)
        }) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24), contentDescription = "Previous Month")
        }
        Text(
            text = SimpleDateFormat("yyyy년 MM월", Locale.KOREA).format(calendar.time),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = {
            val newCalendar = calendar.clone() as Calendar
            newCalendar.add(Calendar.MONTH, 1)
            onMonthChanged(newCalendar)
        }) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24), contentDescription = "Next Month")
        }
    }
}