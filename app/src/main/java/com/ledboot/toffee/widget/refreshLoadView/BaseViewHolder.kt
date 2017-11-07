package com.ledboot.toffee.widget.refreshLoadView

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView

/**
 * Created by Gwynn on 17/10/16.
 */
class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


    private var views: SparseArray<View> = SparseArray()
    var mAdapter: BaseQuickAdapter<*, *>? = null


    public fun setVisible(@IdRes viewId: Int, visible: Boolean): BaseViewHolder {
        var view: View = getView(viewId)
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        return this
    }

    public fun <T : View> getView(@IdRes viewId: Int): T {
        var view = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }

    public fun setAdapter(@IdRes viewId: Int, adapter: Adapter): BaseViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view.adapter = adapter
        return this
    }
}