package com.ledboot.toffee

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.adapter.GirlAdapter
import com.ledboot.toffee.base.BaseFrament
import com.ledboot.toffee.model.Girls
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.network.Retrofits
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_girl.view.*

/**
 * Created by Eleven on 2017/9/13.
 */
class GirlFrament : BaseFrament() {

    var dataList: List<Girls.Results>? = null

    val adapter by lazy { GirlAdapter(context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fra_girl, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        dataList = ArrayList()
        view.girl_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.girl_recycler.adapter = adapter
        initData()
    }

    private fun initData() {
        Retrofits.gankIoService.getBenefitList(10, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ girls ->
                    adapter.setData(girls.results)
                })
    }

    override fun onFirstUserVisible() {
        super.onFirstUserVisible()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }


}