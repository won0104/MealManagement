package com.inconus.mealmanagement.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val deviceToken: String,
    val id: String,
    val password: String
)

data class LoginResponse(
    val result: Int,
    @SerializedName("result_text") val resultText: String,
    @SerializedName("error_message") val errorMessage: String,
    @SerializedName("data") val userDetails: List<UserDetails>
)

data class UserDetails(
    val userId: String,
    val name: String,
    val token: String,
)
