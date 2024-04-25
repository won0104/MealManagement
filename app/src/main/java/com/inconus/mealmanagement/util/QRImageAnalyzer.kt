package com.inconus.mealmanagement.util

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.gson.Gson
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.inconus.mealmanagement.model.Employee

class QRImageAnalyzer(private val onQrCodeScanned: (Employee) -> Unit, private val onError: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val reader = MultiFormatReader()

    override fun analyze(imageProxy: ImageProxy) {
        val buffer = imageProxy.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)
        val source = PlanarYUVLuminanceSource(
            data, imageProxy.width, imageProxy.height, 0, 0, imageProxy.width, imageProxy.height, false
        )
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        try {
            val result = reader.decode(binaryBitmap)
            val employee = Gson().fromJson(result.text, Employee::class.java)
            onQrCodeScanned(employee)
        } catch (e: Exception) {
            //onError("QR 코드 스캔 실패 \n ${e.localizedMessage} ")
        } finally {
            imageProxy.close()
        }
    }
}
