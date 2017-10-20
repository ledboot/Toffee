package com.ledboot.toffee

import android.app.Application
import cn.com.bsfit.dfp.android.FRMS

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