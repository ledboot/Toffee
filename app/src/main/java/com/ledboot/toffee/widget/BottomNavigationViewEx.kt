package com.ledboot.toffee.widget

import android.content.Context
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MenuItem
import android.view.View
import java.lang.reflect.Field

/**
 * Created by Gwynn on 17/9/21.
 */
open class BottomNavigationViewEx : BottomNavigationView, BottomNavigationView.OnNavigationItemSelectedListener {


    var mViewPager: ViewPager? = null
    var mMenuView: BottomNavigationMenuView? = null
    var mButtons: Array<BottomNavigationItemView>? = null
    var mOnPageChangeListener: ExOnPageChangeListener? = null
    var mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener? = null
    var mPrevioutsPosition: Int = -1
    var mSmoothScroll: Boolean = false
    var mItemArray: SparseArray<Int>? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setupWithViewPager(viewPage: ViewPager) {
        setupWithViewPager(viewPage, false)
    }

    fun setupWithViewPager(viewPager: ViewPager, smoothScroll: Boolean) {

        if (viewPager != null) {
            mViewPager = viewPager

        } else {
            mViewPager = null
            super.setOnNavigationItemSelectedListener(null)
            return
        }
        this.mSmoothScroll = smoothScroll
        mItemArray = SparseArray()
        val size: Int = menu.size()
        for (i in 0 until size) {
            mItemArray!!.put(menu.getItem(i).itemId, i)
        }

        if (mOnPageChangeListener == null) {
            mOnPageChangeListener = ExOnPageChangeListener(this)
        }

        mViewPager!!.addOnPageChangeListener(mOnPageChangeListener)

    }

    fun getBottomNavigationMenuView(): BottomNavigationMenuView {
        if (mMenuView == null) {
            val field: Field = BottomNavigationViewEx::class.java.superclass.getDeclaredField("mMenuView")
            field.isAccessible = true
            mMenuView = field.get(this) as BottomNavigationMenuView
        }
        return mMenuView as BottomNavigationMenuView
    }

    fun getBottomNavigationItemViews(): Array<BottomNavigationItemView> {
        val menuView = getBottomNavigationMenuView()
        val field: Field = menuView::class.java.getDeclaredField("mButtons")
        field.isAccessible = true
        mButtons = field.get(menuView) as Array<BottomNavigationItemView>
        return mButtons as Array<BottomNavigationItemView>
    }

    fun setCurrentItem(item: Int) {
        if (item < 0 || item >= maxItemCount) {
            throw ArrayIndexOutOfBoundsException("item is out of bounds, we expected 0 - " + (maxItemCount - 1) + ". Actually " + item)
        }
        val menuView = getBottomNavigationMenuView()
        val buttons = getBottomNavigationItemViews()
        val field: Field = menuView::class.java.getDeclaredField("mOnClickListener")
        field.isAccessible = true
        val onClickListener: View.OnClickListener = field.get(menuView) as View.OnClickListener
        onClickListener.onClick(buttons[item])
    }

    fun getMenuItemPosition(item: MenuItem): Int {
        /*val itemId: Int = item.itemId
        val size: Int = menu.size()
        for (i in 0 until size) {
            if (menu.getItem(i).itemId == itemId)
                return i
        }
        return -1*/
        return mItemArray!!.get(item.itemId)
    }


    override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
        if (mOnNavigationItemSelectedListener == null) {
            mOnNavigationItemSelectedListener = listener
        }
        super.setOnNavigationItemSelectedListener(listener)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (mPrevioutsPosition == getMenuItemPosition(item))
            return true

        mOnNavigationItemSelectedListener!!.onNavigationItemSelected(item)

        mViewPager!!.setCurrentItem(item.itemId, mSmoothScroll)

        mPrevioutsPosition = getMenuItemPosition(item)

        return true
    }

    class ExOnPageChangeListener : ViewPager.OnPageChangeListener {

        private var bottomNavigation: BottomNavigationViewEx? = null

        constructor(bottomNavigation: BottomNavigationViewEx) {
            this.bottomNavigation = bottomNavigation
        }


        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            bottomNavigation!!.setCurrentItem(position)
        }

    }

}