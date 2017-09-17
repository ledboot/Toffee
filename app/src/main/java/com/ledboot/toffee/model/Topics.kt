package com.ledboot.toffee.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Gwynn on 17/9/6.
 */

data class Topic(var id: String, var authorId: String, var tab: String,
                 var content: String, var title: String, var lastReplyAt: String,
                 var good: Boolean, var top: Boolean, var replyCount: Int,
                 var visitCount: Int, var createAt: String, var author: Author) {
    data class Author(@SerializedName("loginname") var loginName: String, var avatarUrl: String)
}