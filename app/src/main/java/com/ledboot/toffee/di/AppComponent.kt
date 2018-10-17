package com.ledboot.toffee.di

import com.ledboot.toffee.AppLoader
import com.ledboot.toffee.di.androidx.AndroidxInjectionModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidxInjectionModule::class,
            AppModule::class,
            ActivityBindingModule::class
        ]
)
interface AppComponent : AndroidInjector<AppLoader> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AppLoader>()
}