package com.ledboot.toffee

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory

/**
 * Created by Gwynn on 17/8/31.
 */

class AppLoader : Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: AppLoader
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL)
    }
}