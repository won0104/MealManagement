package com.inconus.mealmanagement.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/Account/login")
    fun userLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}
