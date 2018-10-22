package com.ledboot.toffee.module.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ledboot.toffee.model.Topics
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    val refreshing = MutableLiveData<Boolean>().apply { false }
    val loading = MutableLiveData<Boolean>().apply { false }
    val data: LiveData<List<Topics.Data>>

    var currentPage = 0

    init {
        data = homeRepository.getTopics(currentPage)
    }

}