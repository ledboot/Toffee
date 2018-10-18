package com.ledboot.toffee.di.androidx

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.internal.Beta

@Beta
class AndroidxInjection private constructor() {

    val TAG: String = "com.ledboot.toffee.di"

    companion object {
        fun inject(fragment: Fragment) {
            checkNotNull(fragment)
            val hasAndroidxFragmentInjector = findHasFragmentInjector(fragment)
            var fragmentInjector: AndroidInjector<Fragment> = hasAndroidxFragmentInjector.androidxFragmentInjector()
            checkNotNull(fragmentInjector)
            fragmentInjector.inject(fragment)
        }

        fun findHasFragmentInjector(fragment: Fragment): HasAndroidxFragmentInjector {
            var parentFragment: Fragment? = fragment
            while (parentFragment!!.parentFragment != null) {
                parentFragment = parentFragment!!.parentFragment
                if (parentFragment is HasAndroidxFragmentInjector) {
                    return parentFragment
                }
            }
            if (fragment.activity is HasAndroidxFragmentInjector) {
                return fragment.activity as HasAndroidxFragmentInjector
            }
            throw IllegalArgumentException(String.format("No injector was found for %s", fragment::class.java.canonicalName))
        }
    }
}