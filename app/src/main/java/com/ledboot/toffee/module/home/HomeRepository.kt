package com.ledboot.toffee.module.home

import androidx.lifecycle.MutableLiveData
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.service.CnodeService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class HomeRepository @Inject constructor(private val cnodeService: CnodeService) {

    val pageSize = 10
    val topicsPath = "topics"

    fun getTopics(data: MutableLiveData<List<Topics.Data>>, currentPage: Int) {
        cnodeService.topicsList(topicsPath, currentPage, pageSize, false)
                .subscribeOn((Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    for (data: Topics.Data in it.data) {
                        data.handleContent()
                    }
                    data.value = it.data
                }, {
                    it.printStackTrace()
                })
    }
}