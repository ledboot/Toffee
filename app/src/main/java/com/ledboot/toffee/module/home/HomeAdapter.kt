package com.ledboot.toffee.module.home

import com.ledboot.toffee.R
import com.ledboot.toffee.model.Topics

class HomeAdapter : ExAdapter<Topics.Data>(R.layout.topic_item) {


    override fun getObjForPosition(position: Int): Topics.Data {
        return mData!!.get(position)
    }


}