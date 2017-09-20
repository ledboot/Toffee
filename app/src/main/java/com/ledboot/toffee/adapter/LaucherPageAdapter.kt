package com.ledboot.toffee.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.ledboot.toffee.base.BaseFrament

/**
 * Created by Gwynn on 2017/9/20.
 */
class LaucherPageAdapter(context: Context?, fragmentManager: FragmentManager, framentList: Array<BaseFrament>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return framentList!!.get(position)
    }

    var context: Context? = null
    var framentList: Array<BaseFrament>? = null

    init {
        this.context = context
        this.framentList = framentList
    }


    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return false
    }

    override fun getCount() = framentList!!.size
}