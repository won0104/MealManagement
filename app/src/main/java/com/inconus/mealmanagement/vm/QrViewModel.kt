package com.inconus.mealmanagement.vm

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.model.EmployeeRepository
import com.inconus.mealmanagement.model.SummaryRepository
import kotlinx.coroutines.launch


class QrViewModel(
    private var employeeRepository: EmployeeRepository,
    private var summaryRepository: SummaryRepository
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

    private val _insertResult = MutableLiveData<Boolean>()
    val insertResult: LiveData<Boolean> = _insertResult

    // QR 스캔 결과 처리
    fun scanSuccess(employee: Employee) {
        viewModelScope.launch {
            try {
                Log.d("확인", "QrViewModel- 입력 레코드: $employee")
                val isInserted = employeeRepository.insertRecord(employee)
                _insertResult.postValue(isInserted)
                if (!isInserted) {
                    _errorMessage.postValue("이전에 스캔된 코드 입니다!!")// 삽입 실패 시 에러 메시지 업데이트
                }
            } catch (e: Exception) {
                Log.e("확인", "QrViewModel- Error inserting record", e)
                _insertResult.postValue(false)
                scanFailure("Error inserting record")
            }
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

//    private val _duplication = MutableLiveData<Boolean>()
//    val duplication: LiveData<Boolean> = _duplication
}

class QrViewModelFactory(
    private val employeeRepository: EmployeeRepository,
    private val summaryRepository: SummaryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QrViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QrViewModel(employeeRepository, summaryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

