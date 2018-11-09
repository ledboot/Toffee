package com.ledboot.toffee.module.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.widget.refreshLoadView.RefreshView
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel(), RefreshView.RefreshListener {


    override fun loadMore() {
        Debuger.logD("loadMore")
    }

    override fun refresh() {
        refreshing.value = true
        currentPage = 1
        homeRepository.getTopics(data, currentPage)
    }

    val refreshing = MutableLiveData<Boolean>().apply { false }
    var loading = MutableLiveData<Boolean>().apply { false }


    val data: MutableLiveData<List<Topics.Data>> = MutableLiveData<List<Topics.Data>>()

    var currentPage = 1

    init {
        homeRepository.getTopics(data, currentPage)
    }

}