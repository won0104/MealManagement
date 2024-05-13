package com.inconus.mealmanagement.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun QrScanningScreen(viewModel: QrViewModel,close:()->Unit) {
    val context = LocalContext.current
    val errorMessage by viewModel.errorMessage.observeAsState("")
    val cameraSelector by viewModel.cameraSelector.observeAsState(CameraSelector.DEFAULT_BACK_CAMERA)

    // 화면 진입 시 1번만 에러 메시지 및 다이얼로그 초기화
    LaunchedEffect(Unit) {
        viewModel.clearErrorState()
    }

    val showErrorDialog = viewModel.showErrorDialog.observeAsState(false)

    // 에러 발생시(=errorMessage 변경 시) 다이얼로그 표시
    LaunchedEffect(errorMessage) {
        viewModel.updateShowErrorDialog(errorMessage.isNotEmpty())
    }

    if (showErrorDialog.value) {
        ErrorDialog(showErrorDialog.value, errorMessage,
            onDismiss = { viewModel.clearErrorState() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        // QR 화면
        QrCodeCameraPreview(cameraSelector) { result ->
            handleCameraResult(result, viewModel, context)
        }

        // 아이콘이랑 텍스트
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(0.dp,0.dp,0.dp,80.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //아이콘 2개
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.TopEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(15.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { viewModel.toggleCamera() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_cameraswitch_24),
                            contentDescription = "카메라 전환 아이콘",
                            modifier = Modifier.size(33.dp),
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { close() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "화면 닫기 아이콘",
                            modifier = Modifier.size(33.dp),
                            tint = Color.White
                        )
                    }
                }
            }
            Text(
                "QR 코드 인식중",
                color = Color.White
            )
        }
    }
}


// QR 스캔 결과 처리
private fun handleCameraResult(
    result: Result<Employee>,
    viewModel: QrViewModel,
    context: Context
) {
    result.fold(
        onSuccess = {
            viewModel.scanSuccess(it)
            Toast.makeText(context, "${it.name}님, 스캔 되었습니다.", Toast.LENGTH_SHORT).show()
        },
        onFailure = {
            viewModel.scanFailure(it.message ?: "알 수 없는 에러")
        }
    )
}