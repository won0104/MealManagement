package com.inconus.mealmanagement.util

import android.content.Context

class SharedPreferencesTokenProvider(context: Context) : TokenProvider {
    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    override fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    override fun setToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    override fun deleteToken(){
        sharedPreferences.edit().remove("token").apply()
    }
}
