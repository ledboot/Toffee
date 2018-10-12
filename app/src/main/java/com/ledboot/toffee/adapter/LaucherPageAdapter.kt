package com.ledboot.toffee.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Gwynn on 2017/9/20.
 */
class LaucherPageAdapter(fm: FragmentManager, private val framentList: Array<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return framentList.get(position)
    }

    override fun getCount() = framentList.size
}