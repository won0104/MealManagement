package com.inconus.mealmanagement.ui.qr

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun PermissionDeniedDialog(showDialog: MutableState<Boolean>, onDismiss: () -> Unit, onGoToSettings: () -> Unit) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("권한 필요") },
            text = { Text("앱을 사용하기 위해서는 카메라 권한이 필요합니다. 설정에서 권한을 부여해주세요.") },
            confirmButton = {
                Button(onClick = onGoToSettings) {
                    Text("설정으로 이동")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("취소")
                }
            }
        )
    }
}
