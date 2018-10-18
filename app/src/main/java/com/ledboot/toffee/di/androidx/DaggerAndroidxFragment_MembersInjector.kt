package com.ledboot.toffee.di.androidx

import androidx.fragment.app.Fragment
import dagger.MembersInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Provider

class DaggerAndroidxFragment_MembersInjector(
        private val childFragmentInjectorProvider: Provider<DispatchingAndroidInjector<Fragment>>) : MembersInjector<DaggerAndroidxFragment> {

    override fun injectMembers(instance: DaggerAndroidxFragment) {
        injectChildFragmentInjector(instance, childFragmentInjectorProvider.get())
    }

    companion object {

        @JvmStatic
        fun create(
                childFragmentInjectorProvider: Provider<DispatchingAndroidInjector<Fragment>>): MembersInjector<DaggerAndroidxFragment> {
            return DaggerAndroidxFragment_MembersInjector(childFragmentInjectorProvider)
        }

        @JvmStatic
        fun injectChildFragmentInjector(
                instance: DaggerAndroidxFragment, childFragmentInjector: DispatchingAndroidInjector<Fragment>) {
            instance.childFragmentInjector = childFragmentInjector
        }
    }
}