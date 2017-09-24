package com.ledboot.toffee.widget

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by Gwynn on 2017/9/24.
 */
open class RefreshRecyclerView : FrameLayout, SwipeRefreshLayout.OnRefreshListener {


    var mRefreshListener: RefreshListener? = null
    var mEndLessScrollListener: EndLessScrollListener
    var mSwipeRefresh: SwipeRefreshLayout
    var mRecyclerView: RecyclerView


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mSwipeRefresh = SwipeRefreshLayout(context)
        mRecyclerView = RecyclerView(context)
        val fraLayoutPamars = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val vgLayoutPamars = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mSwipeRefresh.layoutParams = fraLayoutPamars
        mRecyclerView.layoutParams = vgLayoutPamars
        mSwipeRefresh.addView(mRecyclerView)
        addView(mSwipeRefresh)

        mEndLessScrollListener = EndLessScrollListener()
        mRecyclerView.addOnScrollListener(mEndLessScrollListener)
        mSwipeRefresh.setOnRefreshListener(this)
    }


    override fun onRefresh() {

    }


    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        mRecyclerView.layoutManager = layoutManager
        val linearLayoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager
        mEndLessScrollListener.setLayoutManager(linearLayoutManager)
    }


    fun setRefreshListener(listener: RefreshListener) {
        this.mRefreshListener = listener
    }

    class EndLessScrollListener : RecyclerView.OnScrollListener() {

        var mLinearLayoutManager: LinearLayoutManager? = null

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (dy < 0) return

        }

        fun setLayoutManager(linearLayoutManager: LinearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager
        }
    }

    interface RefreshListener {
        fun onLoadMore()
        fun onRefresh()
    }

}