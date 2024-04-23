package com.inconus.mealmanagement.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun ErrorDialog(showDialog: MutableState<Boolean>, errorMessage: String) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Column(
                modifier = Modifier
                    .width(310.dp)
                    .clip(RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .size(width = 300.dp, height = 200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Error Icon",
                            tint = Color(0xFFF04438)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ERROR",
                            color = Color(0xFFF04438),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = errorMessage,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(21.dp))
                Button(
                    onClick = { showDialog.value = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A6BE4)), // 버튼 내부 색상 설정
                    modifier = Modifier
                        .size(width = 300.dp, height = 55.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("닫기", color = Color.White)
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialog() {
    val showDialog = remember { mutableStateOf(false) }

    ErrorDialog(showDialog, "에러")
}