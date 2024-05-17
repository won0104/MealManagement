package com.inconus.mealmanagement.util

import android.util.Log
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.gson.Gson
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.gson.JsonSyntaxException
import com.inconus.mealmanagement.model.EmployeeRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QRImageAnalyzer(
    private val onQrCodeScanned: (EmployeeRecord) -> Unit,
    private val onError: (String) -> Unit
) : ImageAnalysis.Analyzer {
    private val reader = MultiFormatReader()

    override fun analyze(imageProxy: ImageProxy) {
        // 카메라에서 캡처된 이미지의 첫 번째 평면의 데이터를 바이트 배열로 읽음
        val buffer = imageProxy.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)

        // 이미지 데이터를 이용하여 ZXing 라이브러리가 처리할 수 있는 형식으로 소스 생성
        val source = PlanarYUVLuminanceSource(
            data, imageProxy.width, imageProxy.height, 0, 0, imageProxy.width, imageProxy.height, false
        )

        // 바이너리 비트맵 생성 - QR 코드 스캔을 위한 준비
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        try {
            // QR 코드 스캔 시도
            val result = reader.decode(binaryBitmap)

            // 스캔 결과를 JSON 형태로 파싱하여 Employee 객체로 변환
            val employeeRecord = Gson().fromJson(result.text, EmployeeRecord::class.java)
            // 날짜와 시간 데이터 추가
            val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH")
            val currentDateTime = LocalDateTime.now().format(formatter).toLong()
            employeeRecord.dateScanned = currentDateTime

            // 스캔 결과를 콜백을 통해 반환
            onQrCodeScanned(employeeRecord)
        } catch (e: JsonSyntaxException) {
            Log.e("에러", "QRImageAnalyzer - JSON 파싱 오류: ${e.localizedMessage}")
            onError("QR 코드 형식이 잘못되었습니다. 올바른 QR 코드를 스캔해주세요.")
        } catch (e: IllegalStateException) {
            Log.e("에러", "QRImageAnalyzer - 잘못된 상태 예외: ${e.localizedMessage}")
            onError("예상한 JSON 객체가 아닙니다. QR 코드를 다시 확인해주세요.")
        } catch (e: Exception) {
            if  (e.localizedMessage != null) {
                Log.e("에러", "QRImageAnalyzer - 일반 오류: ${e.localizedMessage}")
                onError("QR 코드를 처리하는 도중 오류가 발생했습니다. 다시 시도해주세요.")
            }
        } finally {
            // 이미지 처리가 완료되면 ImageProxy를 닫아 리소스 해제
            imageProxy.close()
        }
    }
}
