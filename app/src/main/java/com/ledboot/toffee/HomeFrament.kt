package com.ledboot.toffee

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.base.BaseFrament

/**
 * Created by Gwynn on 17/8/31.
 */

class HomeFrament : BaseFrament() {

    var recycler:RecyclerView?= null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fra_home, container, false)
        initView(view)
        return view
    }

    private fun initView(view:View){
        recycler = view.findViewById(R.id.home_recycler) as RecyclerView
    }
}