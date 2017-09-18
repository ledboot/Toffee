package com.ledboot.toffee

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {

        val str =  "本来提供开发api，目的是为了开发第三方应用或客户端，如果大家用来学习也是好的，但现在很多人太过分了，随意发帖，at，严重影响了社区的用户，故而决定开始严查\r\n\r\n以下情况，直接封号\r\n\r\n- 测试标题\r\n- 无任何内容\r\n- 无意义回复\r\n- 测试帖，5分钟内没有删除\r\n\r\n欢迎大家监督\r\n\r\n封号\r\n\r\n- https://cnodejs.org/user/Mwangzhi\r\n- https://cnodejs.org/user/lw6395\r\n- https://cnodejs.org/user/shengliang74  竟然挑衅，发帖说你来打我呀。。。。\r\n- https://cnodejs.org/user/h5-17 @h5-17\r\n- https://cnodejs.org/user/592php @592php\r\n\r\n----\r\n\r\n20170601更新\r\n\r\nhttps://cnodejs.org/?tab=dev  目前开了一个『客户端测试』专区，以后开发新客户端的同学，帖子直接发到这个专区去。tab 的值是 dev。\r\n\r\n![image.png](//dn-cnode.qbox.me/FundjyBuYk60yqQ-PdKstrPKY-7-)"
        val temp = str.replace("\\r\\n".toRegex(), "")
        System.out.print(temp)
    }
}