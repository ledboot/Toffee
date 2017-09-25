package com.ledboot.toffee.widget

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.ledboot.toffee.utils.SizeUtils

/**
 * Created by Gwynn on 2017/9/24.
 */
open class RefreshView : FrameLayout, SwipeRefreshLayout.OnRefreshListener {


    private var mRefreshListener: RefreshListener? = null
    private var mEndLessScrollListener: EndLessScrollListener
    private var mSwipeRefresh: SwipeRefreshLayout
    private var mRecyclerView: RecyclerView
    var mAutoLoadMore: Boolean = true
    var mAutoRefresh: Boolean = true
    var isOnLoading: Boolean = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mSwipeRefresh = SwipeRefreshLayout(context)
        mRecyclerView = RecyclerView(context)
        val fraLayoutPamars = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val vgLayoutPamars: ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mSwipeRefresh.layoutParams = fraLayoutPamars

        mRecyclerView.layoutParams = vgLayoutPamars

        val verticalPadding = SizeUtils.dp2px(16F)

        mRecyclerView.setPadding(0, verticalPadding, 0, verticalPadding)


        mSwipeRefresh.addView(mRecyclerView)
        addView(mSwipeRefresh)

        mEndLessScrollListener = EndLessScrollListener(this)
        mRecyclerView.addOnScrollListener(mEndLessScrollListener)
        mSwipeRefresh.setOnRefreshListener(this)
    }


    interface RefreshListener {
        fun doLoadMore()
        fun doRefresh()
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        mRecyclerView.layoutManager = layoutManager
        val linearLayoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager
        mEndLessScrollListener.setLayoutManager(linearLayoutManager)
    }

    fun setAdapter(adapter: Adapter<*>) {
        mRecyclerView.adapter = adapter
    }


    fun setRefreshListener(listener: RefreshListener) {
        this.mRefreshListener = listener
    }

    fun setColor(vararg colors: Int) {
        mSwipeRefresh.setColorSchemeColors(*colors)
    }

    class EndLessScrollListener(refreshView: RefreshView) : RecyclerView.OnScrollListener() {

        var mLinearLayoutManager: LinearLayoutManager? = null
        var mScrollState: Int = RecyclerView.SCROLL_STATE_IDLE
        var mRefreshView: RefreshView = refreshView

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            this.mScrollState = newState
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (dy < 0 || mLinearLayoutManager == null || mRefreshView.isOnLoading) return

            val itemCout = mLinearLayoutManager!!.itemCount
            val lastVisiblePos = mLinearLayoutManager!!.findLastVisibleItemPosition()
            if (itemCout > 0 && itemCout == lastVisiblePos + 1 &&
                    (mScrollState == RecyclerView.SCROLL_STATE_DRAGGING || mScrollState == RecyclerView.SCROLL_STATE_SETTLING)) {
                mRefreshView.tryLoadMore()
            }

        }

        fun setLayoutManager(linearLayoutManager: LinearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager
        }
    }


    override fun onRefresh() {
        tryRefresh()
    }

    fun tryRefresh() {
        if (mRefreshListener != null) {
            mSwipeRefresh.isRefreshing = true
            mRefreshListener!!.doRefresh()
        }
    }

    fun tryLoadMore() {
        if (mRefreshListener != null) {
            isOnLoading = true
            mRefreshListener!!.doLoadMore()
        }
    }

    fun refreshFinish() {
        mSwipeRefresh.isRefreshing = false
    }

    fun onLoadFinish() {
        isOnLoading = false
    }
}