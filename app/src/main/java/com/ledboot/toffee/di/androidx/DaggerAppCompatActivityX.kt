package com.ledboot.toffee.di.androidx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

abstract class DaggerAppCompatActivityX : AppCompatActivity(), HasFragmentInjector, HasAndroidxFragmentInjector {

    @Inject
    @JvmField
    var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>? = null

    @Inject
    @JvmField
    var androidxFragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return frameworkFragmentInjector!!
    }

    override fun androidxFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment> {
        return androidxFragmentInjector!!
    }

}