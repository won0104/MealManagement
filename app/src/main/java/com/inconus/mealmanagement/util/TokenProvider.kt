package com.inconus.mealmanagement.util

interface TokenProvider {
    fun getToken(): String?
}