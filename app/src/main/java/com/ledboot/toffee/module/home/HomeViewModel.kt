package com.ledboot.toffee.module.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ledboot.toffee.data.RefreshViewState
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.widget.refreshLoadView.RefreshView
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel(), RefreshView.RefreshListener {


    override fun loadMore() {
        currentPage++
        homeRepository.getTopics(currentPage)
    }

    override fun refresh() {
        currentPage = 1
        homeRepository.getTopics(currentPage)
    }


    private val refreshState = MutableLiveData<RefreshViewState>()

    val data: LiveData<List<Topics.Data>> = MutableLiveData<List<Topics.Data>>()

    var currentPage = 1

    init {
        homeRepository.getTopics(currentPage)
    }

}