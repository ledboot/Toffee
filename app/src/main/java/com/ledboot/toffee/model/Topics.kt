package com.ledboot.toffee.model

import com.google.gson.annotations.SerializedName
import com.ledboot.toffee.utils.StringUtils

/**
 * Created by Gwynn on 17/9/6.
 */

data class Topics(var success: Boolean, var data: List<Data>) {
    data class Data(var id: String, var authorId: String, var tab: String,
                    var content: String, var title: String, var lastReplyAt: String,
                    var good: Boolean, var top: Boolean, var replyCount: Int,
                    var visitCount: Int, var createAt: String, var author: Author) {
        data class Author(@SerializedName("loginname") var loginName: String, var avatarUrl: String)
        fun handleContent(){
            StringUtils.removeBlank(content)
        }
    }
}