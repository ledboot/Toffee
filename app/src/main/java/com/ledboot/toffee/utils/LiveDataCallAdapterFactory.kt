package com.ledboot.toffee.utils

import androidx.lifecycle.LiveData
import com.ledboot.toffee.api.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {

        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }

        val oberservableType = getParameterUpperBound(0, returnType as ParameterizedType)

        val rawObservableType = getRawType(oberservableType)

        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }

        if (oberservableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }

        val bodyType = getParameterUpperBound(0, oberservableType)

        return LiveDataCallAdapter<Any>(bodyType)
    }

}