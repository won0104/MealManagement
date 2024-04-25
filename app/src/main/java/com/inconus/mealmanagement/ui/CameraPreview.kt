package com.inconus.mealmanagement.ui

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.util.QRImageAnalyzer

@Composable
fun CameraPreview(onResult: (Result<Employee>) -> Unit){
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(factory = { context ->
        PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(surfaceProvider)
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

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageAnalyzer)
                } catch (e: Exception) {
                    Log.e("CameraPreview", "카메라 바인딩 실패", e)
                    onResult(Result.failure(e))
                }
            }, ContextCompat.getMainExecutor(context))
        }
    })
}