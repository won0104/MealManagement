package com.inconus.mealmanagement.ui

import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun QrScanningScreen(viewModel: QrViewModel, close: () -> Unit) {
    val context = LocalContext.current
    val errorMessage by viewModel.errorMessage.observeAsState("")
    val cameraSelector by viewModel.cameraSelector.observeAsState(CameraSelector.DEFAULT_BACK_CAMERA)
    val insertResult by viewModel.insertResult.observeAsState()
    val processScan by viewModel.processScan.observeAsState(false)

    val scanSuccessMessage = stringResource(id = R.string.scan_success_message)
    val scanDuplicationMessage = stringResource(id = R.string.scan_duplication_message)
    val unknownError = stringResource(id = R.string.unkonwn_error)

    LaunchedEffect(insertResult) {
        insertResult?.let { isSuccess ->
            val message = if (isSuccess) scanSuccessMessage else scanDuplicationMessage
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
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
            if(processScan) {
                handleCameraResult(result, viewModel, unknownError)
            }
        }
        // QR 촬영 버튼 클릭 시
        if(processScan) {
            // 아이콘이랑 텍스트
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(0.dp, 0.dp, 0.dp, 20.dp),
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
                            Icon(
                                painter = painterResource(id = R.drawable.ico_camera),
                                contentDescription = stringResource(id = R.string.camera_switch_icn),
                                //modifier = Modifier.size(.dp),
                                tint = Color.White,
                                modifier = Modifier.clickable { viewModel.toggleCamera() }
                            )
                        Spacer(modifier = Modifier.width(20.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ico_close),
                                contentDescription = stringResource(id = R.string.camera_close_icn),
                               modifier = Modifier.clickable { close() },
                                tint = Color.White
                            )

                    }
                }
                Text(
                    stringResource(id = R.string.qr_access_ing),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        else{
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center){
                Text(
                    stringResource(id = R.string.qr_button_message),
                    color = Color.White,
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// QR 스캔 결과 처리
private fun handleCameraResult(
    result: Result<Employee>,
    viewModel: QrViewModel,
    errorMessage : String
) {


    result.fold(
        onSuccess = {
            viewModel.scanSuccess(it)
        },
        onFailure = {
            viewModel.scanFailure(it.message ?: errorMessage)
        }
    )
}