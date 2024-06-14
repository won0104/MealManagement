package com.inconus.mealmanagement.auth

interface UserCredentialsProvider {
    fun getToken(): String?
    fun setToken(token: String)
    fun getUserId(): String?
    fun setUserId(userId: String)
    fun getUserPassword(): String?
    fun setUserPassword(password: String)
    fun clearUser()
}
