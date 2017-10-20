package com.ledboot.toffee.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.ledboot.toffee.R
import com.ledboot.toffee.model.Girls
import com.ledboot.toffee.widget.BaseQuickAdapter
import com.ledboot.toffee.widget.BaseViewHolder
import kotlinx.android.synthetic.main.girl_item.view.*

/**
 * Created by Gwynn on 2017/9/20.
 */
class GirlAdapter(context: Context?) : BaseQuickAdapter<Girls.Results, BaseViewHolder>(R.layout.girl_item) {

    var mContext: Context? = null

    init {
        mContext = context
    }

    override fun convert(holder: BaseViewHolder, item: Girls.Results?) {

        Glide.with(mContext).load(item!!.url).into(holder.itemView.img_girl)
    }

}