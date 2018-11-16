package com.ledboot.toffee.api

import com.ledboot.toffee.model.Girls
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Gwynn on 2017/9/20.
 */
interface GankIoService {

    @GET("data/福利/{pageSize}/{pageNum}")
    fun getBenefitList(@Path("pageSize") pageSize: Int, @Path("pageNum") pageNum: Int): Flowable<Girls>
}