package com.ledboot.toffee.di.androidx

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


abstract class DaggerAndroidxFragment : Fragment(), HasAndroidxFragmentInjector {

    @Inject
    @JvmField
    var childFragmentInjector: DispatchingAndroidInjector<Fragment>? = null

    override fun onAttach(context: Context?) {
        AndroidxInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidxFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector!!
    }

}