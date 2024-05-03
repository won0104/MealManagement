package com.inconus.mealmanagement.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.inconus.mealmanagement.util.cameraPermission
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun QrPermissionScreen(viewModel: QrViewModel, navController: NavController) {
    val context = LocalContext.current
    val cameraPermission = cameraPermission(viewModel)
    Log.d("확인용","퍼미션 ${cameraPermission}")
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

            Button(onClick = {navController.navigate("qrScanner")}) {
                Text("QR 촬영하기")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("앱을 사용하기 위해서는 카메라 권한이 필요합니다.")
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    navigateToSettings(context)
                    viewModel.updateShowPermissionDialog(false)
                }) {
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

