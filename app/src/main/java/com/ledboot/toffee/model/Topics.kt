package com.ledboot.toffee.model

import androidx.room.Embedded
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

/**
 * Created by Gwynn on 17/9/6.
 */
data class Topics(@Ignore var success: Boolean, @Ignore var data: List<Data>) {
    data class Data(var id: String, var authorId: String, var tab: String,
                    var content: String, var title: String, var lastReplyAt: String,
                    var good: Boolean, var top: Boolean, var replyCount: Int,
                    var visitCount: Int, var createAt: String, @field:Embedded(prefix = "author_") var author: Author) {
        data class Author(@SerializedName("loginname") var loginName: String, var avatarUrl: String)


        override fun toString(): String {
            return "Data(id='$id', authorId='$authorId', tab='$tab', content='$content', title='$title', lastReplyAt='$lastReplyAt', good=$good, top=$top, replyCount=$replyCount, visitCount=$visitCount, createAt='$createAt', author=$author)"
        }

    }
}