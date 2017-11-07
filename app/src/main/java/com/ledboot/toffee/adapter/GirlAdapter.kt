package com.ledboot.toffee.adapter

import com.bumptech.glide.Glide
import com.ledboot.toffee.R
import com.ledboot.toffee.model.Girls
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.widget.refreshLoadView.BaseQuickAdapter
import com.ledboot.toffee.widget.refreshLoadView.BaseViewHolder
import kotlinx.android.synthetic.main.girl_item.view.*

/**
 * Created by Gwynn on 2017/9/20.
 */
class GirlAdapter : BaseQuickAdapter<Girls.Results, BaseViewHolder>(R.layout.girl_item) {

    override fun convert(helper: BaseViewHolder, item: Girls.Results) {
        helper!!.itemView.img_girl.setOriginalSize(50,50)
        Glide.with(mContext).load(item.url).centerCrop().into(helper.itemView.img_girl)
    }

}