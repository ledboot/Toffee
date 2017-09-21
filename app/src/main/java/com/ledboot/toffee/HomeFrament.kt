package com.ledboot.toffee

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ledboot.toffee.adapter.HomeListAdapter
import com.ledboot.toffee.base.BaseFrament
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.network.Retrofits
import com.ledboot.toffee.utils.Debuger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_home.view.*

/**
 * Created by Gwynn on 17/8/31.
 */

class HomeFrament : BaseFrament() {

    private var TAG: String = HomeFrament::class.java.simpleName

    var dataList: List<Topics.Data>? = null

    val adapter by lazy { HomeListAdapter(context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Debuger.logD("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fra_home, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        dataList = ArrayList()
        view.home_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.home_recycler.adapter = adapter
        initData()
    }

    private fun initData() {
        Retrofits.cnodeService.topicsList("topics", 1, 10, false)
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

    override fun onFirstUserVisible() {
        super.onFirstUserVisible()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }
}

