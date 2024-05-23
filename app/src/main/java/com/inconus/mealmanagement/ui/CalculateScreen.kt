package com.inconus.mealmanagement.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inconus.mealmanagement.util.MonthSelector
import com.inconus.mealmanagement.util.convertDate
import java.util.Calendar

data class MockRecord(val dateScanned: String, val peopleCount: Int)

@Composable
fun CalculateScreen() {
    var selectedCalendar by remember { mutableStateOf(Calendar.getInstance()) }
    val mockRecords = listOf(
        MockRecord("20240510", 20),
        MockRecord("20240511", 20),
        MockRecord("20240512", 20),
        MockRecord("20240513", 20),
        MockRecord("20240514", 20),
        MockRecord("20240515", 20),
        MockRecord("20240516", 20),
        MockRecord("20240517", 20)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "정산 관리",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        // 월 변경
        MonthSelector(selectedCalendar) { newCalendar ->
            selectedCalendar = Calendar.getInstance().apply {
                timeInMillis = newCalendar.timeInMillis
            }
        }

        // 총 금액 출력
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .padding(10.dp)
        ) {
            Text("총 식수 인원 : ")
            Text("총 식수 금액 : ")
        }

        // Record Summary list
        LazyColumn(
            modifier = Modifier
                .weight(0.9f)
                .padding(16.dp)
        ) {
            items(mockRecords.size) { index ->
                CalculateCard(record = mockRecords[index])
            }
        }
        Box(modifier = Modifier.weight(0.1f))
    }
}

@Composable
fun CalculateCard(record: MockRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        backgroundColor = Color(0xFFE0D5E6)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = convertDate(record.dateScanned),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "식수 인원: ${record.peopleCount}명",
            )
            Text(
                text = "총액: ${record.peopleCount * 8000}원",
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CalculateScreenPreview() {
    CalculateScreen()
}