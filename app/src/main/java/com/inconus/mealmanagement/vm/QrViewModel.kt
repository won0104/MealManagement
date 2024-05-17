package com.inconus.mealmanagement.vm

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.inconus.mealmanagement.model.EmployeeRecord
import com.inconus.mealmanagement.model.EmployeeRepository
import com.inconus.mealmanagement.model.RecordSummaryRepository
import kotlinx.coroutines.launch


class QrViewModel(
    private var employeeRepository: EmployeeRepository,
    private var recordSummaryRepository: RecordSummaryRepository
) : ViewModel() {
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
    fun scanSuccess(employeeRecord: EmployeeRecord) {
        viewModelScope.launch {
            employeeRepository.insertRecord(employeeRecord)
        }
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


        var data = employeeRepository.getRecordsBetweenDates(2024051712,2024051716)

//    private val _duplication = MutableLiveData<Boolean>()
//    val duplication: LiveData<Boolean> = _duplication
}

class QrViewModelFactory(
    private val employeeRepository: EmployeeRepository,
    private val recordSummaryRepository: RecordSummaryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QrViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QrViewModel(employeeRepository, recordSummaryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

