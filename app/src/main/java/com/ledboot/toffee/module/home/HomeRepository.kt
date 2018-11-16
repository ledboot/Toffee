package com.ledboot.toffee.module.home

import androidx.lifecycle.LiveData
import com.ledboot.toffee.api.ApiResponse
import com.ledboot.toffee.api.CnodeService
import com.ledboot.toffee.model.Topics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class HomeRepository @Inject constructor(private val cnodeService: CnodeService) {

    val pageSize = 10
    val topicsPath = "topics"

    fun getTopics(currentPage: Int): LiveData<ApiResponse<List<Topics>>> {
        return cnodeService.topicsList(topicsPath, currentPage, pageSize, false)
    }
}