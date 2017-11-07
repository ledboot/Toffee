package com.ledboot.toffee.widget.refreshLoadView

abstract class AbstractExpandableItem<T> : IExpandable<T> {
    protected var mExpandable = false
    protected var mSubItems: List<T>? = null

    override fun isExpanded(): Boolean {
        return mExpandable
    }

    override fun setExpanded(expanded: Boolean) {
        mExpandable = expanded
    }

    override fun getSubItems(): List<T>? {
        return mSubItems
    }

    fun hasSubItem(): Boolean {
        return mSubItems != null && mSubItems!!.size > 0
    }

    fun setSubItems(list: List<T>) {
        mSubItems = list
    }

    fun getSubItem(position: Int): T? {
        return if (hasSubItem() && position < mSubItems!!.size) {
            mSubItems!![position]
        } else {
            null
        }
    }

    fun getSubItemPosition(subItem: T): Int {
        return if (mSubItems != null) mSubItems!!.indexOf(subItem) else -1
    }

    fun addSubItem(subItem: T) {
        if (mSubItems == null) {
            mSubItems = ArrayList()
        }
        (mSubItems as ArrayList).add(subItem)
    }

    fun addSubItem(position: Int, subItem: T) {
        if (mSubItems != null && position >= 0 && position < mSubItems!!.size) {
            (mSubItems as ArrayList).add(position, subItem)
        } else {
            addSubItem(subItem)
        }
    }

    operator fun contains(subItem: T): Boolean {
        return mSubItems != null && mSubItems!!.contains(subItem)
    }

    fun removeSubItem(subItem: T): Boolean {
        return mSubItems != null && (mSubItems as ArrayList).remove(subItem)
    }

    fun removeSubItem(position: Int): Boolean {
        if (mSubItems != null && position >= 0 && position < mSubItems!!.size) {
            (mSubItems as ArrayList).removeAt(position)

            return true
        }
        return false
    }
}