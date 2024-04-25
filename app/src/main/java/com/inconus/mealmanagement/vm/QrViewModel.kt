package com.inconus.mealmanagement.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inconus.mealmanagement.model.Employee

class QrViewModel: ViewModel() {
    private val _showPermissionDialog = MutableLiveData(false)
    val showPermissionDialog: LiveData<Boolean> = _showPermissionDialog

    private val _showErrorDialog = MutableLiveData(false)
    val showErrorDialog: LiveData<Boolean> = _showErrorDialog

    private val _duplication = MutableLiveData<Boolean>()
    val duplication: LiveData<Boolean> = _duplication

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _employeeData = MutableLiveData<Employee?>()
    val employeeData: LiveData<Employee?> = _employeeData

    fun updateShowPermissionDialog(showDialog: Boolean) {
        _showPermissionDialog.value = showDialog
    }

    fun updateShowErrorDialog(showDialog: Boolean) {
        _showErrorDialog.value = showDialog
    }

    fun scanSuccess(employee: Employee) {
        _employeeData.value = employee
    }

    fun scanFailure(message: String) {
        _errorMessage.value = message
        _showErrorDialog.value = true // Automatically show the permission dialog on error
    }

    init {
        _errorMessage.observeForever { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                updateShowErrorDialog(true) // Show permission dialog if there's an error message
            }
        }
    }
}
