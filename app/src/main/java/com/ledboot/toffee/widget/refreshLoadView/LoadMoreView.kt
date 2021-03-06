package com.ledboot.toffee.widget.refreshLoadView

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes

/**
 * Created by Gwynn on 17/10/16.
 */
abstract class LoadMoreView {

    var loadMoreStatus = STATUS_DEFAULT
    var mLoadMoreEndGone = false

    companion object {
        val STATUS_DEFAULT: Int = 1
        val STATUS_LOADING: Int = 2
        val STATUS_FAIL: Int = 3
        val STATUS_END: Int = 4
    }

    fun convert(holder: BaseViewHolder<*>) {
        when (loadMoreStatus) {
            STATUS_DEFAULT -> {
                visibleLoading(holder, false)
                visibleLoadFail(holder, false)
                visibleLoadEnd(holder, false)
            }
            STATUS_LOADING -> {
                visibleLoading(holder, true)
                visibleLoadFail(holder, false)
                visibleLoadEnd(holder, false)
            }
            STATUS_FAIL -> {
                visibleLoading(holder, false)
                visibleLoadFail(holder, true)
                visibleLoadEnd(holder, false)
            }
            STATUS_END -> {
                visibleLoading(holder, false)
                visibleLoadFail(holder, false)
                visibleLoadEnd(holder, true)
            }
        }
    }


    @SuppressLint("ResourceType")
    private fun visibleLoading(holder: BaseViewHolder<*>, visible: Boolean) {
        holder.setVisible(loadingViewId, visible)
    }

    @SuppressLint("ResourceType")
    private fun visibleLoadFail(holder: BaseViewHolder<*>, visible: Boolean) {
        holder.setVisible(loadingFailViewId, visible)
    }

    @SuppressLint("ResourceType")
    private fun visibleLoadEnd(holder: BaseViewHolder<*>, visible: Boolean) {
        if (loadingEndViewId != 0) {
            holder.setVisible(loadingEndViewId, visible)
        }
    }

    fun isLoadEndMoreGone(): Boolean {
        if (loadingEndViewId == 0) return true
        return mLoadMoreEndGone
    }

    @get:LayoutRes
    abstract val layoutId: Int

    @get:LayoutRes
    abstract val loadingViewId: Int

    @get:LayoutRes
    abstract val loadingFailViewId: Int

    @get:LayoutRes
    abstract val loadingEndViewId: Int

}