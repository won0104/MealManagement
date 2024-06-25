package com.inconus.mealmanagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.model.Summary
import com.inconus.mealmanagement.util.MonthSelector
import com.inconus.mealmanagement.util.convertDate
import com.inconus.mealmanagement.vm.CalculateViewModel
import java.util.Calendar

@Composable
fun CalculateScreen(viewModel: CalculateViewModel) {
    val selectedCalendar by viewModel.selectedCalendar.observeAsState(initial = Calendar.getInstance())
    val summaries by viewModel.summaries.observeAsState(initial = emptyList())
    val totalCount by viewModel.totalCount.observeAsState(0)
    val totalAmount by viewModel.totalAmount.observeAsState(0)

    // 화면에 진입하면, 변경될 데이터가 있는지 확인 -> 업데이트
    LaunchedEffect(true) {
        viewModel.updateSummary()
    }

    BoxWithConstraints {
        val padding20 = (maxWidth.value * 0.05f).dp.coerceAtLeast(10.dp)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 사용자가 날짜를 선택할 수 있는 UI 컴포넌트
            MonthSelector(selectedCalendar) { newCalendar ->
                viewModel.updateSelectedCalendar(newCalendar)
            }

            // 총 금액 출력
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding20)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding20, padding20, padding20, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(id = R.string.total_people_count_label),
                        style = MaterialTheme.typography.bodyMedium.copy(colorResource(R.color.gray))
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                )
                            ) {
                                append("$totalCount")
                            }
                            append(stringResource(id = R.string.unit_people))

                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding20, padding20 * 0.5f, padding20, padding20),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(id = R.string.total_amount_label),
                        style = MaterialTheme.typography.bodyMedium.copy(colorResource(R.color.gray))
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("$totalAmount")
                            }
                            append(stringResource(id = R.string.unit_currency))
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(0.9f)
                    .padding(padding20,0.dp,padding20,padding20)
            ) {
                items(summaries.size) { index ->
                    CalculateCard(record = summaries[index])
                }
            }
            Box(modifier = Modifier.weight(0.1f))
        }
    }
}

@Composable
fun CalculateCard(record: Summary) {
    BoxWithConstraints {
        val padding20 = (maxWidth.value * 0.05f).dp.coerceAtLeast(10.dp)
        val padding10 = (maxWidth.value * 0.025f).dp.coerceAtLeast(5.dp)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = padding10),
            backgroundColor = Color.White,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
            ) {
                Text(
                    modifier = Modifier.padding(start = padding20, top = padding20),
                    text = convertDate(record.date) + stringResource(id = R.string.unit_date),
                    style = MaterialTheme.typography.labelSmall,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding20, padding10, padding20, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.people_count_label),
                        style = MaterialTheme.typography.bodySmall.copy(colorResource(R.color.gray))
                    )
                    Text(
                        "${record.count}" + stringResource(R.string.unit_people),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding20, padding10, padding20, padding20),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.amount_label),
                        style = MaterialTheme.typography.bodySmall.copy(colorResource(R.color.gray))
                    )
                    Text(
                        "${record.count * record.price}" + stringResource(R.string.unit_currency),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
