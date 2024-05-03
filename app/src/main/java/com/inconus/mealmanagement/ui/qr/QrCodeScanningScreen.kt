package com.inconus.mealmanagement.ui.qr

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.ui.ErrorDialog
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun QrScanningScreen(viewModel: QrViewModel) {
    val context = LocalContext.current
    val errorMessage by viewModel.errorMessage.observeAsState("")
    val cameraSelector by viewModel.cameraSelector.observeAsState(CameraSelector.DEFAULT_BACK_CAMERA)

    // 화면 진입 시 1번만 에러 메시지 및 다이얼로그 초기화
    LaunchedEffect(Unit) {
        viewModel.clearErrorState()
        Log.d("확인용","안")
    }
    Log.d("확인용","밖")

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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        QrCodeCameraPreview(cameraSelector) { result ->
            handleCameraResult(result, viewModel, context)
        }
    }

    Box(
        modifier = Modifier
            .padding(18.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0xFF6650a4))
            .clickable {
                viewModel.toggleCamera()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_cameraswitch_24),
            contentDescription = "카메라 전환 아이콘",
            modifier = Modifier.size(33.dp),
            tint = Color.White
        )
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