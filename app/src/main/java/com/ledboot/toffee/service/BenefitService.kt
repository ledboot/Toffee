package com.ledboot.toffee.service

import com.ledboot.toffee.model.Girls
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Gwynn on 17/9/20.
 */
interface BenefitService {

    @GET("data/福利/{pageSize}/{pageNum}")
    fun getBenefitList(@Path("pageSize") pageSize: String,
                       @Path("pageNum") pageNum: String): Flowable<Girls>
}