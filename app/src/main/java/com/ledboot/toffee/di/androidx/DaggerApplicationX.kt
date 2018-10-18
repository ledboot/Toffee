package com.ledboot.toffee.di.androidx

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

abstract class DaggerApplicationX : dagger.android.DaggerApplication(), HasAndroidxFragmentInjector {

    @Inject
    @JvmField
    var androidxFragmentInjector: DispatchingAndroidInjector<Fragment>? = null


    override fun androidxFragmentInjector(): AndroidInjector<Fragment> {
        return androidxFragmentInjector!!
    }

    abstract override fun applicationInjector(): AndroidInjector<out DaggerApplication>
}