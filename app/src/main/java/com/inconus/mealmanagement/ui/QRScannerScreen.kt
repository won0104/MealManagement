package com.inconus.mealmanagement.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.nav.navigateToSettings
import com.inconus.mealmanagement.util.cameraPermission
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun QrScannerScreen(viewModel: QrViewModel) {
    val context = LocalContext.current
    val errorMessage by viewModel.errorMessage.observeAsState("")

    // 화면 진입 시 에러 메시지 및 다이얼로그 초기화
    LaunchedEffect(Unit) {
        viewModel.clearErrorState()
    }

    val showErrorDialog = viewModel.showErrorDialog.observeAsState(false)


    //Error 발생시(=errorMessage 변경 시) 다이얼로그 표시
    LaunchedEffect(errorMessage) {
        viewModel.updateShowErrorDialog(errorMessage.isNotEmpty())
    }

    if (showErrorDialog.value) {
        Log.d("에러", "screen errorDialog가 true 일 때, - ${errorMessage}")
        ErrorDialog(showErrorDialog.value, errorMessage,
            onDismiss = { viewModel.clearErrorState() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        CameraPreview { result ->
            handleCameraResult(result, viewModel, context)
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