package com.ledboot.toffee.model

/**
 * Created by Gwynn on 17/9/20.
 */
data class Girls(var error: Boolean, var data: List<Data>) {
    data class Data(var _id: String, var createdAt: String, var desc: String, var publishedAt: String, var source: String, var type: String, var url: String, var used: Boolean, var who: String)
}