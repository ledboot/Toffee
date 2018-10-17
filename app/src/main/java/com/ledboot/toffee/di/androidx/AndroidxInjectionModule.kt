package com.ledboot.toffee.di.androidx

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.multibindings.Multibinds


@Module(includes = arrayOf(AndroidInjectionModule::class))
abstract class AndroidxInjectionModule private constructor() {

    @Multibinds
    internal abstract fun androidxFragmentInjectorFactories(): Map<Class<out Fragment>, AndroidInjector.Factory<out Fragment>>

}