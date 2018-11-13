package com.ledboot.toffee.widget.refreshLoadView

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Created by Gwynn on 2017/9/24.
 */
open class RefreshView : FrameLayout, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var mRefreshListener: RefreshListener? = null
    private var mSwipeRefresh: SwipeRefreshLayout
    private var mRecyclerView: RecyclerView
    var isRefresh: Boolean = false
    var ctx: Context? = null
    var mAdapter: BaseQuickAdapter<*, *>? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        ctx = context
        mSwipeRefresh = SwipeRefreshLayout(context)
        mRecyclerView = RecyclerView(context)
        val fraLayoutPamars = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val vgLayoutPamars: ViewGroup.LayoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mSwipeRefresh.layoutParams = fraLayoutPamars

        mRecyclerView.layoutParams = vgLayoutPamars

        mSwipeRefresh.addView(mRecyclerView)

        addView(mSwipeRefresh)

        mSwipeRefresh.setOnRefreshListener(this)

    }

    override fun onLoadMoreRequested() {
        mRefreshListener!!.loadMore()
    }


    interface RefreshListener {
        fun loadMore()
        fun refresh()
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        mRecyclerView.layoutManager = layoutManager
    }

    fun setAdapter(adapter: BaseQuickAdapter<*, *>) {
        mAdapter = adapter
        mRecyclerView.adapter = adapter
        mAdapter!!.setOnLoadMoreListener(this, mRecyclerView)

    }

    fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration)
    }


    fun setOnRefreshListener(listener: RefreshListener) {
        this.mRefreshListener = listener
    }

    fun setColor(vararg colors: Int) {
        mSwipeRefresh.setColorSchemeColors(*colors)
    }

    override fun onRefresh() {
        if (mAdapter!!.isLoading()) {
            mSwipeRefresh.isRefreshing = false
            isRefresh = false
        } else {
            tryRefresh()
        }
    }

    fun tryRefresh() {
        if (mRefreshListener != null) {
            mSwipeRefresh.isRefreshing = true
            isRefresh = true
            mRefreshListener!!.refresh()
        }
    }

    fun refreshFinish() {
        mSwipeRefresh.isRefreshing = false
        isRefresh = false
    }

    fun onLoadFinish() {
        mAdapter!!.loadMoreComplete()
    }

    fun enableLoadMore(enable: Boolean) {
        mAdapter!!.setEnableLoadMore(enable)
    }

    fun loadMoreFail() {
        mAdapter!!.loadMoreFail()
    }

    fun loadMoreEnd(gone: Boolean) {
        mAdapter!!.loadMoreEnd()
    }
}