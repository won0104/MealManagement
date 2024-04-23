package com.inconus.mealmanagement.auth

import com.inconus.mealmanagement.RetrofitClient
import com.inconus.mealmanagement.test.PushTestResponse
import com.inconus.mealmanagement.util.SharedPreferencesTokenProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository (private val tokenProvider: SharedPreferencesTokenProvider){

    private val apiService: UserService = RetrofitClient.retrofit.create(UserService::class.java)

    fun userLogin(data: LoginRequest, onSuccess: (LoginResponse) -> Unit, onError: (String) -> Unit) {
        apiService.userLogin(data).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.result == 0) {
                        onSuccess(responseBody)
                        tokenProvider.setToken(responseBody.userDetails.firstOrNull()?.token ?: "")
                    } else { // result가 -1일때
                        onError("로그인 실패: ${response.body()?.errorMessage}")
                    }
                } else { //400 ~ 500 등등 HTTP 응답 에러
                    onError("로그인 실패: HTTP 코드 ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onError("네트워크 오류: ${t.message}")
                // IOE..
            }
        })
    }
}
