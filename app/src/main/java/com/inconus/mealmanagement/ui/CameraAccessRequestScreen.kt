package com.inconus.mealmanagement.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.util.manageCameraPermission
import com.inconus.mealmanagement.vm.QrViewModel


// 카메라 권한 확인
@Composable
fun CameraAccessRequestScreen(viewModel: QrViewModel) {
    val context = LocalContext.current
    // 카메라 접근 권한 확인
    val cameraPermission = manageCameraPermission(viewModel)
    val showPermissionDialog = viewModel.showPermissionDialog.observeAsState(false)

    // 접근 권한
    val processScan by viewModel.processScan.observeAsState(false)

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
                    QrScanningScreen(viewModel) { viewModel.updateProcessScan(false) }
                }
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
                            contentDescription = stringResource(id = R.string.qr_permission),
                            tint = Color.White
                        )
                        Text(
                            stringResource(id = R.string.qr_access_message),
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
                            text = stringResource(id = R.string.camera_premission_setting),
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                    }
                }
            }

            if (processScan) {
                Button(
                    onClick = { viewModel.updateProcessScan(false) },
                    enabled = cameraPermission,
                    modifier = Modifier
                        .padding(padding20)
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(id = R.string.qr_close), style = MaterialTheme.typography.labelLarge)
                }

            } else {
                Button(
                    onClick = { viewModel.updateProcessScan(true) },
                    enabled = cameraPermission,
                    modifier = Modifier
                        .padding(padding20)
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(id = R.string.qr_scan), style = MaterialTheme.typography.labelLarge)
                }
            }
        }
        PermissionDeniedDialog(
            showPermissionDialog.value,
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

