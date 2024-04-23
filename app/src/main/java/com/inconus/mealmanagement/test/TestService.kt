package com.inconus.mealmanagement.test

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface TestService {
    @POST("api/Push/push/pusttest")
    fun pushTest(
        @Query("token") token: String
    ): Call<PushTestResponse>
}