package com.ledboot.toffee.module.girl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ledboot.toffee.adapter.GirlAdapter
import com.ledboot.toffee.base.ListBaseFragment
import com.ledboot.toffee.model.Girls
import com.ledboot.toffee.network.Retrofits
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.widget.refreshLoadView.BaseQuickAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_list.view.*

/**
 * Created by Eleven on 2017/9/13.
 */
class GirlFragment : ListBaseFragment(), BaseQuickAdapter.OnItemClickListener {

    public val TAG = GirlFragment::class.java.canonicalName

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val mode: Girls.Results = adapter.getItem(position) as Girls.Results
        Debuger.logD(mode.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Debuger.logD("GirlFragment onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Debuger.logD("GirlFragment onCreateView()")
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    val adapter by lazy { GirlAdapter() }

    var currentPage: Int = 1

    private fun initView() {
        view!!.refresh_view.setLayoutManager(GridLayoutManager(context, 3))
        view!!.refresh_view.setAdapter(adapter)
        adapter.setOnItemClickListener(this)
        requestDataFromRemote(true)
    }

    override fun onFirstUserVisible() {
        initView()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    override fun onRefresh() {
        adapter.setEnableLoadMore(false)
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
                view!!.refresh_view.enableLoadMore(true)
                view!!.refresh_view.refreshFinish()
                adapter.setNewData(data)
            }

            false -> {
                view!!.refresh_view.onLoadFinish()
                adapter.addData(data)
            }
        }
    }

    fun requestDataFromRemote(clear: Boolean) {
        Retrofits.gankIoService.getBenefitList(10, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ girls ->
                    loadData(clear, girls.results)
                }, {
                    it.printStackTrace()
                    if (clear) {
                        view!!.refresh_view.refreshFinish()
                    } else {
                        view!!.refresh_view.loadMoreFail()
                    }
                })
    }


}