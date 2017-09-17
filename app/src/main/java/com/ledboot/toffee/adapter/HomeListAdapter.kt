package com.ledboot.toffee.adapter;

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.R
import com.ledboot.toffee.model.Topics
import kotlinx.android.synthetic.main.topic_item.view.*

class HomeListAdapter() : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    val dataList by lazy { ArrayList<Topics.Data>() }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var data:Topics.Data = dataList.get(position)
        var replyVisitCount: String = data.replyCount.toString() + "|" + data.visitCount.toString()
        holder?.itemView?.tv_tab?.text = replyVisitCount
        holder?.itemView?.tv_title?.text = data.title
    }

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(View.inflate(parent?.context, R.layout.topic_item, null))

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView)

    fun setData(data: List<Topics.Data>) {
        dataList.addAll(data)
        notifyDataSetChanged()
    }
}


