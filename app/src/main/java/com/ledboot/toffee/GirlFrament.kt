package com.ledboot.toffee

import android.support.v7.widget.LinearLayoutManager
import com.ledboot.toffee.adapter.GirlAdapter
import com.ledboot.toffee.base.ListBaseFrament
import com.ledboot.toffee.model.Girls
import com.ledboot.toffee.network.Retrofits
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_list.view.*

/**
 * Created by Eleven on 2017/9/13.
 */
class GirlFrament : ListBaseFrament() {


    var dataList: List<Girls.Results>? = null

    val adapter by lazy { GirlAdapter(context) }

    var currentPage: Int = 1

    private fun initView() {
        dataList = ArrayList()
        view!!.refresh_view.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        view!!.refresh_view.setAdapter(adapter)
        initData()
    }

    private fun initData() {
        Retrofits.gankIoService.getBenefitList(10, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    view!!.refresh_view.refreshFinish()
                }
                .subscribe({ girls ->
                    dataList = ArrayList()
                    (dataList!! as ArrayList).addAll(girls.results)
                    adapter.setData(dataList as ArrayList<Girls.Results>)
                })
    }

    override fun onFirstUserVisible() {
        initView()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    override fun onRefresh() {
        currentPage = 1
        initData()
    }

    override fun onLoadMore() {
        ++currentPage
        Retrofits.gankIoService.getBenefitList(10, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    view!!.refresh_view.onLoadFinish()
                }
                .subscribe({ girls ->
                    (dataList!! as ArrayList).addAll(girls.results)
                    adapter.setData(dataList as ArrayList<Girls.Results>)
                })

    }


}