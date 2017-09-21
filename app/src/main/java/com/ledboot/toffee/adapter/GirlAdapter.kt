package com.ledboot.toffee.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ledboot.toffee.model.Girls

/**
 * Created by Gwynn on 2017/9/20.
 */
class GirlAdapter(context: Context?) : RecyclerView.Adapter<GirlAdapter.ViewHolder>() {


    open var context: Context? = null

    init {
        this.context = context
    }

    val dataList by lazy { ArrayList<Girls.Results>() }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var itemView: ImageView = holder?.itemView as ImageView
        Glide.with(context).load(dataList[position].url).into(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var img = ImageView(context)
        return ViewHolder(img)
    }

    override fun getItemCount() = dataList.size

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView)

    fun setData(data: List<Girls.Results>) {
        dataList.addAll(data)
        notifyDataSetChanged()
    }
}