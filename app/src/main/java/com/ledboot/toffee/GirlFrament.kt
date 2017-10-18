package com.ledboot.toffee

import android.support.v7.widget.LinearLayoutManager
import com.ledboot.toffee.adapter.GirlAdapter
import com.ledboot.toffee.base.ListBaseFrament
import com.ledboot.toffee.model.Girls
import com.ledboot.toffee.network.Retrofits
import com.ledboot.toffee.utils.Debuger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_list.view.*

/**
 * Created by Eleven on 2017/9/13.
 */
class GirlFrament : ListBaseFrament() {


    var dataList: List<Girls.Results> = ArrayList()

    val adapter by lazy { GirlAdapter(context) }

    var currentPage: Int = 1

    private fun initView() {
        view!!.refresh_view.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        view!!.refresh_view.setAdapter(adapter)
        requestDataFromRemote(true)
    }

    override fun onFirstUserVisible() {
        initView()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    override fun onRefresh() {
        currentPage = 1
        requestDataFromRemote(true)
    }

    override fun onLoadMore() {
        ++currentPage
        Debuger.logD("currentPage=$currentPage")
        requestDataFromRemote(false)
    }

    fun loadData(clear: Boolean, data: List<Girls.Results>) {
        when (clear) {
            true -> {
                (dataList as ArrayList).clear()
                (dataList as ArrayList).addAll(data)
            }
            false -> (dataList as ArrayList).addAll(data)
        }
        adapter.setData(dataList)
    }

    fun requestDataFromRemote(clear: Boolean) {
        Retrofits.gankIoService.getBenefitList(10, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    view!!.refresh_view.onLoadFinish()
                }
                .subscribe({ girls ->
                    loadData(clear, girls.results)
                })
    }


}