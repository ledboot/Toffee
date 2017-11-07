package com.ledboot.toffee.widget.refreshLoadView

import android.support.annotation.LayoutRes
import android.util.SparseIntArray

abstract class MultiTypeDelegate<T> {
    private var layouts: SparseIntArray? = null
    private var autoMode: Boolean = false
    private var selfMode: Boolean = false

    constructor(layouts: SparseIntArray) {
        this.layouts = layouts
    }

    fun getDefItemViewType(data: List<T>, position: Int): Int {
        val item = data[position]
        return if (item != null) this.getItemType(item) else -255
    }

    protected abstract fun getItemType(var1: T): Int

    fun getLayoutId(viewType: Int): Int {
        return this.layouts!!.get(viewType, -404)
    }

    private fun addItemType(type: Int, @LayoutRes layoutResId: Int) {
        if (this.layouts == null) {
            this.layouts = SparseIntArray()
        }

        this.layouts!!.put(type, layoutResId)
    }

    fun registerItemTypeAutoIncrease(@LayoutRes vararg layoutResIds: Int): MultiTypeDelegate<*> {
        this.autoMode = true
        this.checkMode(this.selfMode)

        for (i in layoutResIds.indices) {
            this.addItemType(i, layoutResIds[i])
        }

        return this
    }

    fun registerItemType(type: Int, @LayoutRes layoutResId: Int): MultiTypeDelegate<*> {
        this.selfMode = true
        this.checkMode(this.autoMode)
        this.addItemType(type, layoutResId)
        return this
    }

    private fun checkMode(mode: Boolean) {
        if (mode) {
            throw RuntimeException("Don\'t mess two register mode")
        }
    }

    companion object {
        private val DEFAULT_VIEW_TYPE = -255
    }
}