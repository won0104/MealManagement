package com.inconus.mealmanagement.auth

import okhttp3.Interceptor
import okhttp3.Response

// HTTP 요청 헤더에 토큰 자동 추가
class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // 토큰을 제공하는 함수로부터 토큰을 가져옴
        val token = tokenProvider()
        if (token == null) {
            // 토큰이 없으면 원본 요청을 그대로 진행
            return chain.proceed(originalRequest)
        }

        // 헤더에 토큰을 추가한 새로운 요청을 생성
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "$token")
            .build()

        return chain.proceed(newRequest)
    }
}
