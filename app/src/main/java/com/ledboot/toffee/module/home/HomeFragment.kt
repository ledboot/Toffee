package com.ledboot.toffee.module.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ledboot.toffee.base.BaseFragment
import com.ledboot.toffee.databinding.FragmentHomeBinding
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.utils.activityViewModelProvider
import com.ledboot.toffee.widget.refreshLoadView.RefreshView
import javax.inject.Inject

/**
 * Created by Gwynn on 17/8/31.
 */

class HomeFragment : BaseFragment() {


    val TAG: String = HomeFragment::class.java.canonicalName

    val adapter by lazy { HomeListAdapter() }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var refreshView: RefreshView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Debuger.logD("HomeFragment onCreate()")
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Debuger.logD("HomeFragment onCreateView()")
        homeViewModel = activityViewModelProvider(viewModelFactory)
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@HomeFragment)
            viewModel = homeViewModel
        }
        refreshView = binding.refreshView
        refreshView.apply {
            setLayoutManager(LinearLayoutManager(context))
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setAdapter(adapter)
            setOnRefreshListener(homeViewModel)

        }
//        refreshView.onLoadFinish()
//        refreshView.refreshFinish()

        homeViewModel.data.observe(this, Observer<List<Topics.Data>> {
            it ?: return@Observer
            adapter.setNewData(it)
        })


        return binding.root
    }

}

