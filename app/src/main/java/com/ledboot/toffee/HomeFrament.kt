package com.ledboot.toffee

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ledboot.toffee.base.BaseFrament
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.service.CnodeService
import com.ledboot.toffee.type.Topic
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import java.util.*

/**
 * Created by Gwynn on 17/8/31.
 */

class HomeFrament : BaseFrament() {

    var TAG: String = HomeFrament::class.java.simpleName

    var recycler: RecyclerView? = null
    var dataList: ArrayList<Topics>? = null

    val cnodeService by lazy {
        CnodeService.create()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fra_home, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        recycler = view.findViewById(R.id.home_recycler) as RecyclerView
        dataList = ArrayList()

        for (i in 1..30) {
            dataList!!.add(Topics(i.toString(), "", "", "", "" + i, Date(), true, true, i, Date(), JSONArray()))
        }
        recycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler?.adapter = HomeListAdapter(dataList!!)
        initData()
    }

    private fun initData() {
        val map = mapOf("page" to 1, "limit" to 50, "mdrender" to false)
        cnodeService.topicsList("topics", map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<List<Topic>>() { list ->
                    list.forEach { it ->
                        Log.d(TAG, "author id = ${it.authorId}")
                    }
                })

    }


    class HomeListAdapter(var list: ArrayList<Topics>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.title?.text = list[position].title
        }

        override fun getItemCount() = list.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(View.inflate(parent?.context, R.layout.topic_item, null))

    }


    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var title = rootView.findViewById(R.id.title) as TextView
    }
}

