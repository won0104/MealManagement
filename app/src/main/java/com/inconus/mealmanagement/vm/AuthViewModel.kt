package com.inconus.mealmanagement.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inconus.mealmanagement.auth.LoginRequest
import com.inconus.mealmanagement.auth.LoginResponse
import com.inconus.mealmanagement.auth.AuthRepository
import com.inconus.mealmanagement.util.SharedPreferencesTokenProvider

class AuthViewModel (application: Application): AndroidViewModel(application) {
    private val tokenProvider = SharedPreferencesTokenProvider(getApplication())
    private val repository = AuthRepository(tokenProvider)

    private val _userId = MutableLiveData<String>()
    private val userId: LiveData<String> = _userId
    fun updateUserId(userId: String) {
        _userId.value = userId
    }

    private val _userPassword = MutableLiveData<String>()
    private val userPassword: LiveData<String> = _userPassword
    fun updateUserPassword(userPassword: String) {
        _userPassword.value = userPassword
    }

    private val _loginStatus = MutableLiveData<Boolean>(false)
    val loginStatus: LiveData<Boolean> = _loginStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    private var _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun loginUser() {
        val id = userId.value ?: ""
        val password = userPassword.value ?: ""

        //유효성 검사
        if (id.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "ID 또는 비밀번호가 입력되지 않았습니다."
            return
        }

        val data = LoginRequest(id = id, password = password, deviceToken = "0")
        repository.userLogin(data, ::handleSuccess, ::handleError)
    }

    // 네트워크 응답 성공
    private fun handleSuccess(response: LoginResponse) {
        _errorMessage.value = response.errorMessage// 잘 성공하면 null 값
        response.userDetails.firstOrNull()?.let {
            _loginStatus.value = true
            _token.value = it.token
            _name.value = it.name
        }
    }

    // 네트워크 응답 실패
    private fun handleError(error: String) {
        _errorMessage.value = error
    }
}


