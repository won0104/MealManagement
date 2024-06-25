package com.inconus.mealmanagement.ui

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.util.QRImageAnalyzer

@Composable
fun QrCodeCameraPreview(
    cameraSelector: CameraSelector,
    onResult: (Result<Employee>) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    // PreviewView의 인스턴스를 remember로 저장하여 상태 유지
    val previewView = remember { PreviewView(context) }
    previewView.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    // 타임스탬프를 저장하는 상태 변수 추가
    var lastScannedTime by remember { mutableLongStateOf(0L) }

    // 전면/후면 카메라 선택 될 때 마다 카메라 다시 바인딩
    LaunchedEffect(cameraSelector) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageAnalyzer = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(ContextCompat.getMainExecutor(context), QRImageAnalyzer(
                    onQrCodeScanned = { employee ->
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastScannedTime >= 3000) {
                            lastScannedTime = currentTime
                            onResult(Result.success(employee))
                        }
                    },
                    onError = { error ->
                        onResult(Result.failure(Exception(error)))
                    }
                ))
            }
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalyzer)
        } catch (e: Exception) {
            Log.e("CameraPreview", "카메라 바인딩 실패", e)
            onResult(Result.failure(e))
        }
    }
    // 라이프사이클에 따른 카메라 해제 처리
    DisposableEffect(lifecycleOwner) {
        onDispose {
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()  // 컴포저블이 제거될 때 카메라 리소스를 해제
        }
    }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
    }
}





