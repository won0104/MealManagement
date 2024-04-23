package com.inconus.mealmanagement.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inconus.mealmanagement.test.PushTestResponse
import com.inconus.mealmanagement.test.TestRepository
import com.inconus.mealmanagement.util.SharedPreferencesTokenProvider

class TestViewModel(application: Application) : AndroidViewModel(application) {
    private val tokenProvider = SharedPreferencesTokenProvider(application.applicationContext)
    private val repository = TestRepository()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _resultText = MutableLiveData<String>()
    val resultText: LiveData<String> = _resultText

    private val _testStatus = MutableLiveData<Boolean>(false)
    val testStatus: LiveData<Boolean> = _testStatus

    fun pushTest() {
        val token = tokenProvider.getToken()?: "Token 없음 "
        repository.pushTest(token,::handleSuccess, ::handleError)
    }
    // 네트워크 응답 성공
    private fun handleSuccess(response: PushTestResponse) {
        _errorMessage.value = response.errorMessage// 잘 성공하면 null 값
        _resultText.value = response.resultText
        _testStatus.value=true
    }

    // 네트워크 응답 실패
    private fun handleError(error: String) {
        _errorMessage.value = error
    }
}
