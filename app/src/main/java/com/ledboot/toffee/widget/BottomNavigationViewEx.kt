package com.ledboot.toffee.widget

import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MenuItem

/**
 * Created by Gwynn on 17/9/21.
 */
open class BottomNavigationViewEx : BottomNavigationView {

    var mViewPager: ViewPager? = null

    var mOnPageChangeListener: ExOnPageChangeListener? = null
    var mOnItemSelectedListener: ExOnNavigationItemSelectedListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {

    }

    fun setupWithViewPager(viewPage: ViewPager, smoothScroll: Boolean) {


        if (viewPage != null) {
            val pageAdapter = viewPage.adapter
            setPagerAdapter(pageAdapter)
        } else {
            setPagerAdapter(null)
        }

        if (mOnPageChangeListener == null) {
            mOnPageChangeListener = ExOnPageChangeListener();
        }

        viewPage.addOnPageChangeListener(mOnPageChangeListener)
    }

    private fun setPagerAdapter(pageAdapter: PagerAdapter?) {
        if (pageAdapter != null) {

        }
    }

    class ExOnPageChangeListener : ViewPager.OnPageChangeListener {


        override fun onPageScrollStateChanged(state: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPageSelected(position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    class ExOnNavigationItemSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener {

        var previousPosition: Int = -1

        constructor(viewPage: ViewPager, smoothScroll: Boolean, listenr: BottomNavigationView.OnNavigationItemSelectedListener)

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}