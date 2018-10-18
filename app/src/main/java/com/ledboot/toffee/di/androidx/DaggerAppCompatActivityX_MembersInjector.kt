package com.ledboot.toffee.di.androidx

import dagger.MembersInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Provider

open class DaggerAppCompatActivityX_MembersInjector(
        private val frameworkFragmentInjectorProvider: Provider<DispatchingAndroidInjector<android.app.Fragment>>,
        private val androidxFragmentInjectorProvider: Provider<DispatchingAndroidInjector<androidx.fragment.app.Fragment>>) : MembersInjector<DaggerAppCompatActivityX> {

    override fun injectMembers(instance: DaggerAppCompatActivityX) {
        injectFrameworkFragmentInjector(instance, frameworkFragmentInjectorProvider.get())
        injectAndroidxFragmentInjector(instance, androidxFragmentInjectorProvider.get())
    }

    companion object {

        fun create(
                frameworkFragmentInjectorProvider: Provider<DispatchingAndroidInjector<android.app.Fragment>>,
                androidxFragmentInjectorProvider: Provider<DispatchingAndroidInjector<androidx.fragment.app.Fragment>>): MembersInjector<DaggerAppCompatActivityX> {
            return DaggerAppCompatActivityX_MembersInjector(
                    frameworkFragmentInjectorProvider, androidxFragmentInjectorProvider)
        }

        @JvmStatic
        fun injectFrameworkFragmentInjector(
                instance: DaggerAppCompatActivityX,
                frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>) {
            instance.frameworkFragmentInjector = frameworkFragmentInjector
        }

        @JvmStatic
        fun injectAndroidxFragmentInjector(
                instance: DaggerAppCompatActivityX,
                androidxFragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>) {
            instance.androidxFragmentInjector = androidxFragmentInjector
        }
    }
}