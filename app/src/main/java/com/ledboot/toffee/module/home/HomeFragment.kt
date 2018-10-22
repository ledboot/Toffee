package com.ledboot.toffee.module.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ledboot.toffee.adapter.HomeListAdapter
import com.ledboot.toffee.base.BaseFragment
import com.ledboot.toffee.databinding.FragmentHomeBinding
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.network.Retrofits
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.utils.activityViewModelProvider
import com.ledboot.toffee.widget.refreshLoadView.RefreshView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fra_list.view.*
import javax.inject.Inject

/**
 * Created by Gwynn on 17/8/31.
 */

class HomeFragment : BaseFragment(), RefreshView.RefreshListener {


    val TAG: String = HomeFragment::class.java.canonicalName

    val adapter by lazy { HomeListAdapter() }

    var currentPage: Int = 1

    var pageSize: Int = 10

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var refreshView: RefreshView

    private var data: List<Topics.Data>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Debuger.logD("HomeFragment onCreate()")
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Debuger.logD("HomeFragment onCreateView()")
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@HomeFragment)
        }
        viewModel = activityViewModelProvider(viewModelFactory)
        binding.viewModel = viewModel
        refreshView = binding.refreshView
        refreshView.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.data.observe(this, Observer<List<Topics.Data>> {
            it ?: return@Observer
            if (it != data) {
                refreshView.setAdapter(adapter)
                data = it
            }
        })
    }

    private fun initView() {
        view!!.refresh_view.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        view!!.refresh_view.setAdapter(adapter)
        requestDataFromRemote(true)
    }

    override fun loadMore() {
        ++currentPage
        requestDataFromRemote(false)
    }

    override fun refresh() {
        currentPage = 1
        requestDataFromRemote(true)
    }

    fun requestDataFromRemote(clear: Boolean) {
        Retrofits.cnodeService.topicsList("topics", currentPage, pageSize, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topics ->
                    for (data: Topics.Data in topics.data) {
                        data.handleContent()
                    }
                    adapter.loadMoreComplete()
                    loadData(clear, topics.data)
                }, {
                    it.printStackTrace()
                    Toast.makeText(context, "error msg = ${it.message}", Toast.LENGTH_SHORT).show()
                })
    }

    fun loadData(clear: Boolean, data: List<Topics.Data>) {
        when (clear) {
            true -> {
                view!!.refresh_view.refreshFinish()
                adapter.setNewData(data)
            }
            false -> {
                adapter.addData(data)
            }
        }
    }

    override fun onFirstUserVisible() {
//        initView()
        super.onFirstUserVisible()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    override fun onUserInvisible() {
        super.onUserVisible()
    }
}

