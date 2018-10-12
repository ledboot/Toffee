package com.ledboot.toffee.di

import com.ledboot.toffee.AppLoader
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ActivityBindingModule::class
        ]
)
interface AppComponent : AndroidInjector<AppLoader> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AppLoader>()
}