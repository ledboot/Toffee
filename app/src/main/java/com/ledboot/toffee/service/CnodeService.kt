package com.ledboot.toffee.service

import com.ledboot.toffee.model.Categories
import com.ledboot.toffee.model.Topics
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Gwynn on 17/9/6.
 */


interface CnodeService {

    @GET("{path}")
    fun topicsList(@Path("path") path: String,
                   @Query("page") page: Int,
                   @Query("limit") limit: Int): Flowable<Topics>

    @GET("{path}")
    fun getCategory(@Path("path") path: String): Flowable<List<Categories>>
}