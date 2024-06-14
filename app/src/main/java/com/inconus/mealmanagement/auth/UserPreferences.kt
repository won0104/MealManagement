package com.inconus.mealmanagement.auth

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class UserPreferences(context: Context) : UserCredentialsProvider {
    private val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    private val masterKeyAlias = MasterKey
        .Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // 암호화
    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        "user_credentials",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun getToken(): String? = sharedPreferences.getString("token", null)

    override fun setToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    override fun getUserId(): String = sharedPreferences.getString("userId", null) ?:""

    override fun setUserId(userId: String) {
        sharedPreferences.edit().putString("userId", userId).apply()
    }

    override fun getUserPassword(): String = encryptedSharedPreferences.getString("password", null) ?:""

    override fun setUserPassword(password: String) {
        encryptedSharedPreferences.edit().putString("password", password).apply()
    }

    override fun clearUser() {
        sharedPreferences.edit().remove("token").apply()
        sharedPreferences.edit().remove("userId").apply()
        encryptedSharedPreferences.edit().remove("password").apply()
    }
}
