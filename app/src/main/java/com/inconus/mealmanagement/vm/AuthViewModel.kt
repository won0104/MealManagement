package com.inconus.mealmanagement.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide.init
import com.inconus.mealmanagement.MyApplication
import com.inconus.mealmanagement.RetrofitClient
import com.inconus.mealmanagement.auth.LoginRequest
import com.inconus.mealmanagement.auth.LoginResponse
import com.inconus.mealmanagement.auth.AuthRepository
import com.inconus.mealmanagement.util.Event
import com.inconus.mealmanagement.auth.UserPreferences

class AuthViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    // token
    private val repository = AuthRepository(userPreferences)

    fun initRetrofitClient() {
        RetrofitClient.setTokenProvider(userPreferences)
    }

    private var _token = MutableLiveData<String>()
    val token: LiveData<String> = _token


    // User
    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId
    fun updateUserId(userId: String) {
        _userId.value = userId
    }

    private val _userPassword = MutableLiveData<String>()
    val userPassword: LiveData<String> = _userPassword
    fun updateUserPassword(userPassword: String) {
        _userPassword.value = userPassword
    }

    fun updateUserInfo(userName: String, userPhone: String) {
        // 추가 구현 필요..
        _name.value = userName
        _userId.value = userPhone
    }

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _loginStatus = MutableLiveData<Boolean>(false)
    val loginStatus: LiveData<Boolean> = _loginStatus

    // 에러 처리
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    fun updateErrorMessage(errorMessage: String) {
        _errorMessage.value = errorMessage
    }

    // 로그인
    fun loginUser(autoLogin: Boolean) {
        val id = userId.value ?: ""
        val password = userPassword.value ?: ""

        //유효성 검사
        if (id.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "로그인 실패 \n ID 또는 비밀번호가 입력되지 않았습니다."
            return
        }

        val data = LoginRequest(id = id, password = password, deviceToken = "0")
        repository.userLogin(data, { response ->
            handleSuccess(response, autoLogin) // handleSuccess
        }, ::handleError)
    }

    private val _loginSuccessEvent = MutableLiveData<Event<Boolean>>()
    val loginSuccessEvent: LiveData<Event<Boolean>> = _loginSuccessEvent

    // 로그인 - 네트워크 응답 성공
    private fun handleSuccess(response: LoginResponse, autoLogin: Boolean) {
        _errorMessage.value = response.errorMessage// 잘 성공하면 null 값
        response.userDetails.firstOrNull()?.let {
            _loginStatus.value = true
            _token.value = it.token
            _name.value = it.name
            _loginSuccessEvent.value = Event(true)
            // 자동 로그인 정보 저장
            if (autoLogin) {
                userPreferences.setUserId(userId.value ?: "")
                userPreferences.setUserPassword(userPassword.value ?: "")
            }
        }
    }

    // 로그인 - 네트워크 응답 실패
    private fun handleError(error: String) {
        _errorMessage.value = error
        Log.d("에러:", " viewModel -  ${errorMessage.value}")
    }

    // 자동 로그인
    fun autoLogin() {
        val userId = userPreferences.getUserId()
        val userPassword = userPreferences.getUserPassword()

        if (userId.isNotEmpty() && userPassword.isNotEmpty()) {
            _userId.value = userId
            _userPassword.value = userPassword
            loginUser(false)  // 자동 로그인시 autoLogin을 false로 설정
        }
    }

    // 로그아웃
    fun logoutUser() {
        userPreferences.clearUser()
        _loginStatus.value = false
        _userId.value = ""
        _userPassword.value = ""
        _token.value = ""
        _name.value = ""
        _errorMessage.value = ""
    }

}
class AuthViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



