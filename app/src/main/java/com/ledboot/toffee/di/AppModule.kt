package com.ledboot.toffee.di

import android.content.Context
import com.ledboot.toffee.AppLoader
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: AppLoader): Context {
        return application.applicationContext
    }
}