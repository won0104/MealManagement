package com.inconus.mealmanagement.test

import com.google.gson.annotations.SerializedName

data class PushTestRequest(
    val token: String
)

data class PushTestResponse(
    val result: Int,
    @SerializedName("result_text") val resultText: String,
    @SerializedName("error_message") val errorMessage: String,
)