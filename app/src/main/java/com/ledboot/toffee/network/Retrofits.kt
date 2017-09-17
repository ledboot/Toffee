package com.ledboot.toffee.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ledboot.toffee.service.CnodeService
import com.ledboot.toffee.utils.Debuger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Eleven on 2017/9/16.
 */
object Retrofits {

    open val TAG: String = Retrofits::class.java.simpleName
    private val retrofit: Retrofit
    private val okHttpClient: OkHttpClient
    private val DEFAULT_TIMEOUT = 5L

    init {

        val baseUrlKy = "http://baobab.kaiyanapp.com/api/v4/"
        val baseUrlCnode = "https://cnodejs.org/api/v1/"
        val logging = Interceptor { chain ->
            val request = chain.request()
            Debuger.logD(TAG, "okhttp --- > " + request.url())
            chain.proceed(request)
        }
        val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()

        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrlCnode)
                .build()
    }

    val cnodeService: CnodeService by lazy {
        retrofit.create(CnodeService::class.java)
    }
}