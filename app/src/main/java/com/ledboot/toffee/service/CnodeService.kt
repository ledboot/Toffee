package com.ledboot.toffee.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ledboot.toffee.type.Topic
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * Created by Gwynn on 17/9/6.
 */


open interface CnodeService {

    @GET("{path}")
    fun topicsList(@Path("path") path: String, @QueryMap options: @JvmSuppressWildcards Map<String, Any>): Observable<List<Topic>>

    companion object {
        val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        val baseUrl = "https://cnodejs.org/api/v1/"

        fun create(): CnodeService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(CnodeService::class.java)
        }
    }
}