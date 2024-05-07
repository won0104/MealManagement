package com.inconus.mealmanagement.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.inconus.mealmanagement.vm.QrViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun manageCameraPermission (viewModel: QrViewModel): Boolean {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    // 카메라 접근 권한, 초기값은 현재 권한 상태
    var hasCameraPermission by remember { mutableStateOf(
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    )}
    // 권한 확인 : ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) / -1이면 권한 없음 0이면 권한 있음

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
        viewModel.updateCameraPermission(isGranted)
        // shouldShowRequestPermissionRationale : 처음 거절시 true, 두번 거절하면 false
        // 권한 X , 완전 거절 (2번 이상) 하면 다이얼로그
        val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.CAMERA)
        if (!isGranted && !shouldShowRationale) {
            //Log.d("확인용","권한 : $shouldShowRationale")
            viewModel.updateShowPermissionDialog(true)
            //Toast.makeText(context,"설정에서 카메라 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    // 한번만 수행
    LaunchedEffect(true) {
        if (!hasCameraPermission) {
            Log.d("확인용","권한 요청 창 생성")
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // 설정에서 돌아왔을 때, 카메라 권한이 허용되어 있으면 새로고침
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) { // 권한을 확인 했을 때, 0인지 확인
                val currentPermissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                if (hasCameraPermission != currentPermissionStatus) { // 값이 바뀌면 업데이트
                    hasCameraPermission = currentPermissionStatus
                    viewModel.updateCameraPermission(currentPermissionStatus)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return hasCameraPermission
}
