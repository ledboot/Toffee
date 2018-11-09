package com.ledboot.toffee.widget.refreshLoadView

import android.util.SparseArray
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Gwynn on 17/10/16.
 */
class BaseViewHolder<T> : RecyclerView.ViewHolder {

    constructor(binding: ViewDataBinding) : this(binding.root) {
        this.binding = binding
    }

    constructor(itemView: View) : super(itemView)

    private var binding: ViewDataBinding? = null
    private var views: SparseArray<View> = SparseArray()
    var mAdapter: BaseQuickAdapter<*, *>? = null


    public fun setVisible(@IdRes viewId: Int, visible: Boolean): BaseViewHolder<T> {
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

    public fun setAdapter(@IdRes viewId: Int, adapter: Adapter): BaseViewHolder<T> {
        val view = getView<AdapterView<*>>(viewId)
        view.adapter = adapter
        return this
    }

    fun bind(data: T) {
        binding!!.setVariable(BR.item, data)
        binding!!.executePendingBindings()
    }
}