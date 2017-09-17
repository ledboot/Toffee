package com.ledboot.toffee

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.adapter.HomeListAdapter
import com.ledboot.toffee.base.BaseFrament
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.network.Retrofits
import com.ledboot.toffee.utils.Debuger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_home.*
import kotlinx.android.synthetic.main.fra_home.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Gwynn on 17/8/31.
 */

class HomeFrament : BaseFrament() {

    var TAG: String = HomeFrament::class.java.simpleName

    var dataList: List<Topics.Data>? = null

    val adapter by lazy { HomeListAdapter() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fra_home, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        dataList = ArrayList()
        for (i in 0..30) {
            (dataList as ArrayList).add(Topics.Data(i.toString(), "", "", "", "", "", true, true, i, i, "", Topics.Data.Author("", "")))
        }
        (dataList as ArrayList)
        view.home_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.home_recycler.adapter = adapter
        initData()
    }

    private fun initData() {
        Retrofits.cnodeService.topicsList("topics", 1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topics ->
                    adapter.setData(topics.data)
                })
    }


}

