package com.ledboot.toffee.data.remote

import com.ledboot.toffee.data.MediatorCase
import com.ledboot.toffee.data.Result
import com.ledboot.toffee.model.Topics
import com.ledboot.toffee.module.home.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class TopicDataCase @Inject constructor(private val homeRepository: HomeRepository) : MediatorCase<Int, Topics.Data>() {


    override fun execute(parameters: Int) {

        result.postValue(Result.Loading)
        val topics = homeRepository.getTopics(parameters)
        result.removeSource(topics)

        result.addSource(topics) {
            when (it) {

            }
        }
    }

}