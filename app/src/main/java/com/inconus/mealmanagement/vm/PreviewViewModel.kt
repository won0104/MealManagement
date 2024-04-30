package com.inconus.mealmanagement.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DummyAuthViewModel : ViewModel() {
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId

    private val _userPassword = MutableLiveData<String>()
    val userPassword: LiveData<String> = _userPassword

    private val _loginStatus = MutableLiveData<Boolean>(false)
    val loginStatus: LiveData<Boolean> = _loginStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun updateUserId(userId: String) {
        _userId.value = userId
    }

    fun updateUserPassword(userPassword: String) {
        _userPassword.value = userPassword
    }

    fun loginUser() {
        val id = userId.value ?: ""
        val password = userPassword.value ?: ""

        if (id.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "ID 또는 비밀번호가 입력되지 않았습니다."
            return
        }

        // 더미 데이터를 사용하여 로그인 상태 변경
        _loginStatus.value = true
        _token.value = "dummyToken"
        _name.value = "Dummy User"
    }
}