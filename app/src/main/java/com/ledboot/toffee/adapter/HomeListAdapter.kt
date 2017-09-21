package com.ledboot.toffee.adapter;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ledboot.toffee.R
import com.ledboot.toffee.model.Topics
import kotlinx.android.synthetic.main.topic_item.view.*

class HomeListAdapter(context: Context) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    var context: Context? = null

    init {
        this.context = context
    }

    val dataList by lazy { ArrayList<Topics.Data>() }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data: Topics.Data = dataList.get(position)

        holder.itemView.tv_reply_count.text = data.replyCount.toString()
        holder.itemView.tv_visit_count.text = data.visitCount.toString()
        holder.itemView.tv_title.text = data.title
        when (data.tab) {
            null -> holder.itemView.tv_tab.text = ""
            "shared" -> holder.itemView.tv_tab.text = "分享"
            "ask" -> holder.itemView.tv_tab.text = "问答"
            else -> holder.itemView.tv_tab.text = "其他"
        }
        holder.itemView.tv_topic_abstract.text = data.content
        Glide.with(context).load(data.author.avatarUrl).centerCrop().into(holder.itemView.img_author_avator)
        holder.itemView.tv_author_name.text = data.author.loginName
        holder.itemView.tv_create_at.text = data.createAt
    }

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(View.inflate(parent?.context, R.layout.topic_item, null))

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView)

    fun setData(data: List<Topics.Data>) {
        dataList.addAll(data)
        notifyDataSetChanged()
    }
}


