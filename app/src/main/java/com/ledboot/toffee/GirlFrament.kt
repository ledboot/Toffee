package com.ledboot.toffee

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
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


    val adapter by lazy { GirlAdapter(context) }

    var currentPage: Int = 1

    private fun initView() {
        view!!.refresh_view.setLayoutManager(GridLayoutManager(context,3))
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
        requestDataFromRemote(false)
    }

    fun loadData(clear: Boolean, data: List<Girls.Results>) {
        when (clear) {
            true -> {
                view!!.refresh_view.refreshFinish()
                adapter.setNewData(data)
            }

            false -> adapter.addData(data)
        }
    }

    fun requestDataFromRemote(clear: Boolean) {
        Retrofits.gankIoService.getBenefitList(10, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ girls ->
                    loadData(clear, girls.results)
                })
    }


}