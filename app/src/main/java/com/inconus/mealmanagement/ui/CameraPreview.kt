package com.inconus.mealmanagement.ui

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.util.QRImageAnalyzer

@Composable
fun CameraPreview(onResult: (Result<Employee>) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    val context = LocalContext.current

    // 카메라 설정 및 재설정 로직
    fun setupCamera(cameraProvider: ProcessCameraProvider, previewView: PreviewView) {
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageAnalyzer = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(ContextCompat.getMainExecutor(context), QRImageAnalyzer(
                    onQrCodeScanned = { employee ->
                        onResult(Result.success(employee))
                    },
                    onError = { error ->
                        onResult(Result.failure(Exception(error)))
                    }
                ))
            }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageAnalyzer
            )
        } catch (e: Exception) {
            Log.e("CameraPreview", "카메라 바인딩 실패", e)
            onResult(Result.failure(e))
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
        AndroidView(factory = { context ->
            PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener({
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                    setupCamera(cameraProvider, this) // 최초 설정
                }, ContextCompat.getMainExecutor(context))
            }
        }, modifier = Modifier.fillMaxSize(), update = {
            // 카메라 선택기가 변경될 때마다 카메라를 다시 설정
            setupCamera(ProcessCameraProvider.getInstance(context).get(), it)
        })

        // 카메라 전환 아이콘
        Box(
            modifier = Modifier
                .padding(18.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF6650a4))
                .clickable {
                    cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else {
                        CameraSelector.DEFAULT_BACK_CAMERA
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_cameraswitch_24),
                contentDescription = "Camera Switch",
                modifier = Modifier.size(33.dp),
                tint = Color.White
            )
        }
    }
}


