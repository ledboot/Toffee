package com.ledboot.toffee.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.R
import kotlinx.android.synthetic.main.girl_item.view.*

/**
 * Created by Gwynn on 17/9/19.
 */

class GirlAdapter(context: Context) : RecyclerView.Adapter<GirlAdapter.ViewHolder>() {

    var context: Context? = null
    val dataList by lazy { ArrayList<String>() }

    init {
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.itemView.item_tv.text = dataList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(View.inflate(context, R.layout.girl_item, null))

    override fun getItemCount() = dataList.size

    fun setData(data: List<String>) {
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView)
}