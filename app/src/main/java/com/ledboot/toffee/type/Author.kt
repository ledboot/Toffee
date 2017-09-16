package com.ledboot.toffee.type

import com.google.gson.annotations.SerializedName

/**
 * Created by Gwynn on 17/9/15.
 */
data class Author(@SerializedName("loginname") var loginName: String, var avatarUrl: String) {
}