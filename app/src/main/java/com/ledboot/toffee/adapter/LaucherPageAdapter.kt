package com.ledboot.toffee.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.base.BaseFrament

/**
 * Created by Gwynn on 2017/9/20.
 */
class LaucherPageAdapter(context: Context?, fragmentManager: FragmentManager, framentList: Array<Fragment>) : FragmentPagerAdapter(fragmentManager) {


    var context: Context? = null
    var framentList: Array<Fragment>? = null

    init {
        this.context = context
        this.framentList = framentList
    }

    override fun getItem(position: Int): Fragment? {
        return framentList!!.get(position)
    }

    override fun getCount() = framentList!!.size
}