package com.ledboot.toffee.utils

/**
 * Created by Gwynn on 17/9/18.
 */

object StringUtils{
    fun removeBlank(str:String){
        str.trim().replace("\\r\\n".toRegex(),"")
    }
}