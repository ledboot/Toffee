package com.ledboot.toffee.adapter;

import com.bumptech.glide.Glide
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.widget.refreshLoadView.BaseQuickAdapter
import com.ledboot.toffee.widget.refreshLoadView.BaseViewHolder
import kotlinx.android.synthetic.main.topic_item.view.*

class HomeListAdapter : BaseQuickAdapter<Topics.Data, BaseViewHolder>(com.ledboot.toffee.R.layout.topic_item) {

    override fun convert(holder: BaseViewHolder, item: Topics.Data) {
        holder.itemView.tv_reply_count.text = item.replyCount.toString()
        holder.itemView.tv_visit_count.text = item.visitCount.toString()
        holder.itemView.tv_title.text = item.title
        if (item.tab == null || "null".equals(item.tab)) {
            holder.itemView.tv_tab.text = ""
        }
        if (null != item.tab) {
            when (item.tab) {
                "shared" -> holder.itemView.tv_tab.text = "分享"
                "ask" -> holder.itemView.tv_tab.text = "问答"
                else -> holder.itemView.tv_tab.text = "其他"
            }
        } else {
            holder.itemView.tv_tab.text = ""
        }

        holder.itemView.tv_topic_abstract.text = item.content
        Glide.with(mContext).load(item.author.avatarUrl).centerCrop().into(holder.itemView.img_author_avator)
        holder.itemView.tv_author_name.text = item.author.loginName
        holder.itemView.tv_create_at.text = item.createAt
    }

}


