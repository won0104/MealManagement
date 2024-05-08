package com.inconus.mealmanagement

import android.app.Application

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }
    //todo oncreate에서 하지말고 init 블록에서 instance 초기화 해줘도 돼요. 선택하기 나름
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
//        instance = this
    }
}
