package com.inconus.mealmanagement.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.util.manageCameraPermission
import com.inconus.mealmanagement.vm.QrViewModel


// 카메라 권한 확인
@Composable
fun CameraAccessRequestScreen(viewModel: QrViewModel, navController: NavHostController) {
    val context = LocalContext.current
    // 카메라 접근 권한 확인
    val cameraPermission = manageCameraPermission(viewModel)
    val showPermissionDialog =
        viewModel.showPermissionDialog.observeAsState(false) as MutableState<Boolean>


    PermissionDeniedDialog(
        showPermissionDialog,
        onDismiss = { viewModel.updateShowPermissionDialog(false) },
        onGoToSettings = {
            navigateToSettings(context)
            viewModel.updateShowPermissionDialog(false)
        })
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (cameraPermission) {
            Button(
                onClick = { navController.navigate("qrScanner") },
                modifier = Modifier
                    .size(130.dp, 130.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize() // Make the column fill the button
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                        contentDescription = "QR 아이콘",
                        modifier = Modifier.size(45.dp) // Size of the icon
                    )
                    Spacer(modifier = Modifier.height(20.dp)) // Space between icon and text
                    Text("QR 촬영하기")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_warning_amber_24), "경고 아이콘",
                    modifier = Modifier.size(65.dp)
                )

                Text(
                    "앱을 사용하기 위해서는 \n카메라 권한이 필요합니다.",
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        navigateToSettings(context)
                        viewModel.updateShowPermissionDialog(false)
                    },
                    modifier = Modifier
                        .height(55.dp)
                        .width(200.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(text = "설정으로 이동")
                }
            }
        }
    }
}


//세부 정보 설정 화면으로 이동 (권한 설정)
fun navigateToSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

