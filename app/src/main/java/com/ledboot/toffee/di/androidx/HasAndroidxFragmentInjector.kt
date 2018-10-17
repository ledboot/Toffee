package com.ledboot.toffee.di.androidx

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.internal.Beta

@Beta
interface HasAndroidxFragmentInjector {

    fun androidxFragmentInjector(): AndroidInjector<Fragment>
}