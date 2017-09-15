package com.ledboot.toffee.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Gwynn on 17/8/31.
 */

open class BaseFrament : Fragment() {

    var isFirstVisiable: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }
}