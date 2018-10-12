package com.ledboot.toffee

import android.content.Context
import com.ledboot.toffee.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by Gwynn on 17/8/31.
 */

class AppLoader : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return DaggerAppComponent.builder().create(this)
    }


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
    }
}