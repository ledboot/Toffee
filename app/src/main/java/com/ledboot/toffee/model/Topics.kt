package com.ledboot.toffee.model

import org.json.JSONArray
import java.util.*

/**
 * Created by Gwynn on 17/9/5.
 */

data class Topics(
        val id:String,
        val authorId:String,
        val tab:String,
        val content:String,
        val title:String,
        val lastReplyAt:Date,
        val good:Boolean,
        val top:Boolean,
        val replyCount:Int,
        val createAt:Date,
        val author:JSONArray
){

}