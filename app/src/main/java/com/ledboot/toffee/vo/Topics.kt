package com.ledboot.toffee.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(tableName = "topic", indices = [Index("id")])
data class Topics(var id: String, @field:SerializedName("author_id") var authorId: String, var tab: String,
                  var content: String, var title: String, var lastReplyAt: String,
                  var good: Boolean, var top: Boolean, var replyCount: Int,
                  var visitCount: Int, var createAt: String, @field:Embedded(prefix = "author_") var author: Author) {
    data class Author(@field:SerializedName("login_name") var loginName: String, @field:SerializedName("avatar_url") var avatarUrl: String)
}