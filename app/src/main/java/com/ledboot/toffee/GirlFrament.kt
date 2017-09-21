package com.ledboot.toffee

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.adapter.GirlAdapter
import com.ledboot.toffee.base.BaseFrament
import kotlinx.android.synthetic.main.fra_girl.view.*

/**
 * Created by Eleven on 2017/9/13.
 */
class GirlFrament : BaseFrament() {

    var dataList: List<String>? = null
    val adapter by lazy { GirlAdapter(context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fra_girl, null, false)
        initView(view)
        return view
    }

    override fun onFirstUserVisible() {
        super.onFirstUserVisible()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }


    private fun initView(view: View) {
        view.girl_recycler.adapter = adapter
        view.girl_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dataList = ArrayList()
        for (i in 1..40) {
            (dataList as ArrayList).add(i.toString())
        }
        adapter.setData(dataList!!)
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }
}