package com.inconus.mealmanagement

import android.app.Application

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
