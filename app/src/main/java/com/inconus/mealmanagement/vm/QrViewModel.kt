package com.inconus.mealmanagement.vm

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inconus.mealmanagement.model.Employee


class QrViewModel : ViewModel() {
    // 카메라 권한 관련
    private val _showPermissionDialog = MutableLiveData(false)
    val showPermissionDialog: LiveData<Boolean> = _showPermissionDialog
    fun updateShowPermissionDialog(showDialog: Boolean) {
        _showPermissionDialog.value = showDialog
    }
    private val _hasCameraPermission = MutableLiveData<Boolean>()
    val hasCameraPermission: LiveData<Boolean> = _hasCameraPermission

    fun updateCameraPermission(isGranted: Boolean) {
        _hasCameraPermission.value = isGranted
        if (!isGranted) {
            updateShowPermissionDialog(true)
        }
    }


    // 에러 관련
    private val _showErrorDialog = MutableLiveData(false)
    val showErrorDialog: LiveData<Boolean> = _showErrorDialog
    fun updateShowErrorDialog(showDialog: Boolean) {
        _showErrorDialog.value = showDialog
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    fun clearErrorState() {
        _errorMessage.value = ""
        _showErrorDialog.value = false
    }


    // QR 스캔 결과 처리
    private val _employeeData = MutableLiveData<Employee?>()
    val employeeData: LiveData<Employee?> = _employeeData
    fun scanSuccess(employee: Employee) {
        _employeeData.value = employee
    }

    fun scanFailure(message: String) {
        _errorMessage.value = message
        Log.e("viewModel", "${_errorMessage.value}")
        _showErrorDialog.value = true
    }


    // 카메라 전환 관련 코드
    var cameraSelector = MutableLiveData(CameraSelector.DEFAULT_BACK_CAMERA)
        private set

    fun toggleCamera() {
        cameraSelector.value = if (cameraSelector.value == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

//    private val _duplication = MutableLiveData<Boolean>()
//    val duplication: LiveData<Boolean> = _duplication
}
