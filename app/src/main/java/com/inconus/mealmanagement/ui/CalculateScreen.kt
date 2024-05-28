package com.inconus.mealmanagement.ui

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inconus.mealmanagement.model.RecordSummary
import com.inconus.mealmanagement.util.MonthSelector
import com.inconus.mealmanagement.util.convertDate
import com.inconus.mealmanagement.vm.CalculateViewModel
import java.util.Calendar

@Composable
fun CalculateScreen(viewModel:CalculateViewModel) {
    Log.d("확인용","시작?")
    val selectedCalendar by viewModel.selectedCalendar.observeAsState(initial = Calendar.getInstance())
    viewModel.updateSummary()
    val summaries by viewModel.summaries.observeAsState(initial = emptyList())
    val totalSummaryCount by viewModel.totalSummaryCount.observeAsState(0)
    val context =LocalContext.current

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
            viewModel.updateSelectedCalendar(newCalendar)
            Toast.makeText(context,"선택한 월${newCalendar.get(Calendar.MONTH) + 1}",Toast.LENGTH_SHORT).show()
        }

        // 총 금액 출력
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .padding(10.dp)
        ) {
            Text("총 식수 인원 : ${totalSummaryCount}명")
            Text("총 식수 금액 : ${totalSummaryCount*8000} 원")
        }

        // Record Summary list
        LazyColumn(
            modifier = Modifier
                .weight(0.9f)
                .padding(16.dp)
        ) {
            items(summaries.size) { index ->
                CalculateCard(record = summaries[index])
            }
        }
        Box(modifier = Modifier.weight(0.1f))
    }
}

@Composable
fun CalculateCard(record: RecordSummary) {
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
                text = convertDate(record.date),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "식수 인원: ${record.count}명",
            )
            Text(
                text = "총액: ${record.count * 8000}원",
            )
        }
    }
}