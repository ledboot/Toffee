package com.ledboot.toffee.api

import okhttp3.Headers
import retrofit2.Response

sealed class ApiResponse<T> {

    companion object {
        fun <T> error(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> success(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResult()
                } else {
                    ApiSuccessResponse(body, response.headers())
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

class ApiEmptyResult<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T, val header: Headers) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

