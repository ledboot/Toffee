package com.ledboot.toffee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.R
import com.ledboot.toffee.widget.refreshLoadView.RefreshView
import kotlinx.android.synthetic.main.fra_list.view.*

/**
 * Created by Gwynn on 2017/9/24.
 */
abstract class ListBaseFragment : BaseFragment(), RefreshView.RefreshListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fra_list, container, false)
        view.refresh_view.setRefreshListener(this)
        return view
    }

    override fun loadMore() {
        onLoadMore()
    }

    override fun refresh() {
        onRefresh()
    }

    protected abstract fun onLoadMore()

    protected abstract fun onRefresh()
}