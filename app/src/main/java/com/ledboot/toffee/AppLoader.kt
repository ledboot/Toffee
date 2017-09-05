package com.ledboot.toffee

import android.app.Application

/**
 * Created by Gwynn on 17/8/31.
 */

class AppLoader : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: AppLoader
    }

    override fun onCreate() {
        super.onCreate()
    }
}