package com.ledboot.toffee

import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.ledboot.toffee.adapter.HomeListAdapter
import com.ledboot.toffee.base.ListBaseFrament
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.network.Retrofits
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_list.view.*

/**
 * Created by Gwynn on 17/8/31.
 */

class HomeFrament : ListBaseFrament() {


    private var TAG: String = HomeFrament::class.java.simpleName

    var dataList: List<Topics.Data>? = null

    val adapter by lazy { HomeListAdapter(context) }

    var currentPage: Int = 1

    var pageSize: Int = 10

    private fun initView() {
        dataList = ArrayList()
        view!!.refresh_view.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        view!!.refresh_view.setAdapter(adapter)
        initData()
    }

    private fun initData() {
        Retrofits.cnodeService.topicsList("topics", currentPage, pageSize, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topics ->
                    for (data: Topics.Data in topics.data) {
                        data.handleContent()
                    }
                    adapter.setData(topics.data)
                }, {
                    it.printStackTrace()
                    Toast.makeText(context, "error msg = ${it.message}", Toast.LENGTH_SHORT).show()
                })
    }

    override fun onLoadMore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRefresh() {
        Retrofits.cnodeService.topicsList("topics", currentPage, pageSize, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    view!!.refresh_view.refreshFinish()
                }
                .subscribe({ topics ->
                    for (data: Topics.Data in topics.data) {
                        data.handleContent()
                    }
                    adapter.setData(topics.data)
                }, {
                    it.printStackTrace()
                    Toast.makeText(context, "error msg = ${it.message}", Toast.LENGTH_SHORT).show()
                })
    }

    override fun onFirstUserVisible() {
        initView()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    override fun onUserInvisible() {
        super.onUserVisible()
    }
}

