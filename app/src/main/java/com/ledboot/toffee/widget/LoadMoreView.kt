package com.ledboot.toffee.widget

import android.support.annotation.LayoutRes

/**
 * Created by Gwynn on 17/10/16.
 */
abstract class LoadMoreView {

    private var mLoadStatus = STATUS_DEFAULT

    companion object {
        val STATUS_DEFAULT: Int = 1
        val STATUS_LOADING: Int = 2
        val STATUS_FAIL: Int = 3
        val STATUS_END: Int = 4
    }

    public fun setLoadStatus(status: Int) {
        mLoadStatus = status
    }

    public fun getLoadStatus(): Int {
        return mLoadStatus
    }


    public fun convert(holder: BaseViewHolder) {
        when (mLoadStatus) {
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


    private fun visibleLoading(holder: BaseViewHolder, visible: Boolean) {
        holder.setVisible(loadingViewId, visible)
    }

    private fun visibleLoadFail(holder: BaseViewHolder, visible: Boolean) {
        holder.setVisible(loadingFailViewId, visible)
    }

    private fun visibleLoadEnd(holder: BaseViewHolder, visible: Boolean) {
        if (loadingEndViewId != 0) {
            holder.setVisible(loadingEndViewId, visible)
        }
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