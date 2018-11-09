package com.ledboot.toffee.module.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ledboot.toffee.utils.Debuger

abstract class ExAdapter<T> : RecyclerView.Adapter<ExViewHolder<T>> {

    protected var mData: List<T>? = null
    protected var mLayoutResId: Int = 0

    constructor(@LayoutRes layoutResId: Int) : this(layoutResId, null)

    constructor(@LayoutRes layoutResId: Int, data: List<T>?) {
        this.mData = data ?: ArrayList()
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExViewHolder<T> {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, mLayoutResId, parent, false)
        return ExViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ExViewHolder<T>, position: Int) {
        var obj: T = getObjForPosition(position)
        holder.bind(obj)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun addData(newData: Collection<T>) {
        Debuger.logD("addData !")
        (mData as ArrayList<T>).addAll(newData)
        notifyDataSetChanged()
    }

    abstract fun getObjForPosition(position: Int): T

}