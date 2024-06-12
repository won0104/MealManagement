package com.inconus.mealmanagement.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.util.manageCameraPermission
import com.inconus.mealmanagement.vm.QrViewModel


// 카메라 권한 확인
@Composable
fun CameraAccessRequestScreen(viewModel: QrViewModel, goToQrScanner: () -> Unit) {
    val context = LocalContext.current
    // 카메라 접근 권한 확인
    val cameraPermission = manageCameraPermission(viewModel)
    val showPermissionDialog =
        viewModel.showPermissionDialog.observeAsState(false) as MutableState<Boolean>
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val maxWidth = maxWidth.value
        val padding20 = (maxWidth * 0.05f).dp.coerceAtLeast(10.dp)
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (cameraPermission) { // 카메라 권한이 있을 때
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .padding(padding20)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF333333)),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 결과 반영 X, 카메라 화면만 띄워둠
                    QrCodeCameraPreview(
                        cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA,
                        onResult = { result -> /* Handle result */ },
                        //modifier = Modifier.weight(1f).fillMaxSize()
                    )
                }
//                Button(
//                    onClick = { goToQrScanner() },
//                    modifier = Modifier
//                        .size(130.dp, 130.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(MaterialTheme.colorScheme.primary),
//                    contentPadding = PaddingValues()
//                ) {
//                    Column(
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ico_qr_line),
//                            contentDescription = "QR 아이콘",
//                            modifier = Modifier.size(45.dp)
//                        )
//                        Spacer(modifier = Modifier.height(padding20))
//                        Text("QR 촬영하기")
//                    }
//                }
            } else { // QR 권한이 없을 때
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .padding(padding20)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF333333)),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ico_qr_line),
                            contentDescription = "QR 스캔 아이콘",
                            tint = Color.White
                        )
                        Text(
                            "QR코드 스캔을 위해\n카메라 권한을 허용해주세요",
                            modifier = Modifier.padding(top = 10.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                    }
                    Button(
                        onClick = {
                            navigateToSettings(context)
                            viewModel.updateShowPermissionDialog(false)
                        },
                        modifier = Modifier
                            .height(40.dp)
                            .width(170.dp)
                            .border(1.dp, Color.White, RoundedCornerShape(30.dp))
                            .background(Color(0xFF333333)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333))
                    )
                    {
                        Text(
                            text = "카메라 권한 설정",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                    }
                }
            }

            Button(
                onClick = { goToQrScanner()},
                enabled = cameraPermission,
                modifier = Modifier
                    .padding(padding20)
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "QR 촬영")
            }
        }

        PermissionDeniedDialog(
            showPermissionDialog,
            onDismiss = { viewModel.updateShowPermissionDialog(false) },
            onGoToSettings = {
                navigateToSettings(context)
                viewModel.updateShowPermissionDialog(false)
            }
        )
    }
}

//세부 정보 설정 화면으로 이동 (권한 설정)
fun navigateToSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

