package com.inconus.mealmanagement

import com.inconus.mealmanagement.auth.AuthInterceptor
import com.inconus.mealmanagement.auth.UserPreferences

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.inconus.kr/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private var tokenProvider: UserPreferences? = null


    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor { tokenProvider?.getToken()?:"" })
        .addInterceptor(logging)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun setTokenProvider(provider: UserPreferences) {
        tokenProvider = provider
    }
}