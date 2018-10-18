package com.ledboot.toffee.di

import com.ledboot.toffee.LauncherActivity
import com.ledboot.toffee.LauncherModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {


    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                LauncherModule::class
            ]
    )
    abstract fun launcherActivity(): LauncherActivity
}