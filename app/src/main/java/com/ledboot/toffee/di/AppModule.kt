package com.ledboot.toffee.di

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ledboot.toffee.AppLoader
import com.ledboot.toffee.api.CnodeService
import com.ledboot.toffee.api.GankIoService
import com.ledboot.toffee.network.Retrofits
import com.ledboot.toffee.utils.Debuger
import com.ledboot.toffee.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: AppLoader): Context {
        return application.applicationContext
    }


    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val logging = Interceptor { chain ->
            val request = chain.request()
            Debuger.logD(Retrofits.TAG, "okhttp --- > " + request.url())
            chain.proceed(request)
        }
        val DEFAULT_TIMEOUT = 5L
        return OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())//添加LiveData adapter
    }

    @Provides
    @Singleton
    fun provideCnodeService(builder: Retrofit.Builder): CnodeService {
        val baseUrlCnode = "https://cnodejs.org/api/v1/"
        return builder.baseUrl(baseUrlCnode).build().create(CnodeService::class.java)
    }

    @Provides
    @Singleton
    fun provideGankIoService(builder: Retrofit.Builder): GankIoService {
        val baseUrlGankIo = "http://gank.io/api/"
        return builder.baseUrl(baseUrlGankIo).build().create(GankIoService::class.java)
    }
}