package com.inconus.mealmanagement.ui

import android.annotation.SuppressLint
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PermissionDeniedDialog(
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onGoToSettings: () -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .width(310.dp)
                    .clip(RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .size(width = 300.dp, height = 220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text = "서비스 이용 알림",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = " 카메라에 대한 권한 사용을 거부하였습니다. QR 코드를 인식하기 위해서는 카메라 권한이 필요합니다.\n 기능 사용을 원하실 경우 휴대폰 설정 > 애플리케이션 관리자에서 해당 앱의 권한을 허용해주세요.",
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(21.dp))
                Row(){
                    Button(onClick = { onDismiss() },
                        modifier = Modifier
                            .size(width = 140.dp, height = 50.dp),
                        shape = RoundedCornerShape(8.dp)) {
                        Text("닫기")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { onGoToSettings()},
                        modifier = Modifier
                            .size(width = 140.dp, height = 50.dp),
                        shape = RoundedCornerShape(8.dp)) {
                        Text("설정")
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewPermissionDialog() {
    val showDialog = mutableStateOf(true)
    PermissionDeniedDialog(showDialog, {}) {}
}
