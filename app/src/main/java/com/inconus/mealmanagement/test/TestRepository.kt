package com.inconus.mealmanagement.test

import com.inconus.mealmanagement.RetrofitClient
import com.inconus.mealmanagement.auth.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestRepository {
    private val apiService: TestService = RetrofitClient.retrofit.create(TestService::class.java)

    fun pushTest( token:String, onSuccess: (PushTestResponse) -> Unit, onError: (String) -> Unit) {
        apiService.pushTest(token).enqueue(object : Callback<PushTestResponse> {
            override fun onFailure(call: Call<PushTestResponse>, t: Throwable) {
                onError("네트워크 오류: ${t.message}")
            }

            override fun onResponse(
                call: Call<PushTestResponse>,
                response: Response<PushTestResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.result == 0) {
                        onSuccess(responseBody)
                    } else { // result가 -1일때
                        onError("로그인 실패: ${response.body()?.errorMessage}")
                    }
                } else { //400 ~ 500 등등 HTTP 응답 에러
                    onError("로그인 실패: HTTP 코드 ${response.code()}")
                }
            }
        })
    }
}