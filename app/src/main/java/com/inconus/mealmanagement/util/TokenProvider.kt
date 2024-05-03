package com.inconus.mealmanagement.util

interface TokenProvider {
    fun getToken(): String?
    fun setToken(token: String)
    fun deleteToken()
}