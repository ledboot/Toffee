package com.ledboot.toffee

import androidx.lifecycle.ViewModel
import com.ledboot.toffee.base.BaseFrament
import com.ledboot.toffee.module.girl.GirlFrament
import com.ledboot.toffee.module.home.HomeFrament
import com.ledboot.toffee.module.user.UserFrament
import javax.inject.Inject

class LauncherViewModel @Inject constructor() : ViewModel() {

    fun getTabList(): Array<BaseFrament> {
        val fragmentList: Array<BaseFrament> = arrayOf(HomeFrament(), GirlFrament(), UserFrament())
        return fragmentList

    }
}