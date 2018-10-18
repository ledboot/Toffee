package com.ledboot.toffee

import androidx.lifecycle.ViewModel
import com.ledboot.toffee.base.BaseFragment
import com.ledboot.toffee.module.girl.GirlFragment
import com.ledboot.toffee.module.home.HomeFragment
import com.ledboot.toffee.module.user.UserFragment
import javax.inject.Inject

class LauncherViewModel @Inject constructor() : ViewModel() {

    fun getTabList(): Array<BaseFragment> {
        val fragmentList: Array<BaseFragment> = arrayOf(HomeFragment(), GirlFragment(), UserFragment())
        return fragmentList

    }
}