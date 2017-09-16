package com.ledboot.toffee

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.base.BaseFrament
import com.ledboot.toffee.model.Topics

/**
 * Created by Gwynn on 17/8/31.
 */

class HomeFrament : BaseFrament() {

    var recycler: RecyclerView? = null
    var dataList:List<Topics> ? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fra_home, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        recycler = view.findViewById(R.id.home_recycler) as RecyclerView
    }


    override fun onFirstUserVisible() {
        for (i in 0..50){

        }
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    class HomeHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

    class HomeAdapter :RecyclerView.Adapter<HomeHolder>(){

        override fun onBindViewHolder(holder: HomeHolder?, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}