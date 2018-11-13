package com.ledboot.toffee.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/*
SimpleDateFormat https://developer.android.com/reference/java/text/SimpleDateFormat.html
匹配格式
Date转换成年
 G               ---- 公元
 y               ---- 2017
 yy              ---- 17
 yyy             ---- 2017
 yyyy            ---- 2017
 yyyyy           ---- 002017
Date转换成月份格式
 M               ---- 8        （个位数时不以0补齐）
 MM              ---- 08       （个位数时以0补齐）
 MMM             ---- 8月
 MMMM            ---- 八月
 L               ---- 8        （个位数时不以0补齐）
 LL              ---- 08       （个位数时以0补齐）
 LLL             ---- 8月
 LLLL            ---- 八月
Date转换成星期格式
 E/EE/EEE        ---- 周几
 EEEE            ---- 星期几
 EEEEE           ---- 几
Date转换成周数
 w(small)        ---- 年中的周数   （个位数时不以0补齐）
 ww(small)       ---- 年中的周数   （个位数时以0补齐）
 W(big)          ---- 月份中的周数  （个位数时不以0补齐）
 WW(big)         ---- 月份中的周数  （个位数时以0补齐）
Date转换成天数
 d               ---- 1        （日期9月1号）月份中的第几天 （个位数时不以0补齐）
 dd              ---- 01       （日期9月1号）月份中的第几天 （个位数时以0补齐）
 D               ---- 244      （日期9月1号）一年当中的第几天
输出当前时间是上午/下午
 a               ---- 上午
输出当前时间的小时位
 H               ---- 9        （9:00）Hour in day (0-23) 24时制   （个位数时不以0补齐）
 HH              ---- 09       （9:00）Hour in day (0-23) 24时制   （个位数时以0补齐）
 K               ---- 9        （9:00）Hour in am/pm (0-11) 12时制 （个位数时不以0补齐）
 KK              ---- 09       （9:00）Hour in am/pm (0-11) 12时制 （个位数时以0补齐）
输出当前时间的分钟位
 m               ---- 4         分钟数（个位数时不以0补齐）
 mm              ---- 04        分钟数（个位数时以0补齐）
输出当前时间的秒位
 s               ---- 5        （个位数时不以0补齐）
 ss              ---- 05       （个位数时以0补齐）
输出当前时间的毫秒位
 S               ---- 1         保留一位
 SS              ---- 10        保留两位
 SSS             ---- 100       保留三位
时区
 z               ---- GMT+08:00
 zzzz            ---- 中国标准时间
 Z               ---- +0800
*/

/**
 * 当前时间毫秒值
 */
val currentTimeMills: Long get() = System.currentTimeMillis()

internal object DefaultDateFormat {

    private const val DEFAULT_DATE1 = "yyyy-MM-dd HH:mm:ss"
    private const val DEFAULT_DATE2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    val DEFAULT_FORMAT = ThreadLocal<SimpleDateFormat>().apply { set(SimpleDateFormat(DEFAULT_DATE1, Locale.CHINA)) }
    val DEFAULT_FORMAT2 = ThreadLocal<SimpleDateFormat>().apply { set(SimpleDateFormat(DEFAULT_DATE2, Locale.CHINA)) }
}


fun String.parseDateFormat(format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): String {
    return parseString2Date(this, format).format2String()
}

/**
 * 解析String类型的日期为Date类型
 *
 * @param time
 * @param format
 */
fun parseString2Date(time: String, format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()!!): Date {
    return try {
        format.parse(time)
    } catch (e: ParseException) {
        e.printStackTrace()
        Date()
    }
}

fun Date.format2String(format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): String {
    return format.format(this)
}

/**
 * 解析String类型的日期为Long类型
 *
 * @param time
 * @param format
 */
fun parseDateString2Mills(time: String, format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()!!): Long {
    return try {
        format.parse(time).time
    } catch (e: ParseException) {
        e.printStackTrace()
        -1L
    }
}

/**
 * 将时间戳转换成 xx小时前 的样式（同微博）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果在1小时外的今天内，显示今天15:32
 * 如果是昨天的，显示昨天15:32
 * 如果是同一年，显示 09-01 15:32
 * 其余显示，2017-09-01
 */
fun formatAgoStyleForWeibo(time: String, format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()!!): String = parseDateString2Mills(time, format).formatAgoStyleForWeibo()


/**
 * 将时间戳转换成 xx小时前 的样式（同微博）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果在1小时外的今天内，显示今天15:32
 * 如果是昨天的，显示昨天15:32
 * 如果是同一年，显示 09-01 15:32
 * 其余显示，2017-09-01
 */
fun Date.formatAgoStyleForWeibo(): String = this.time.formatAgoStyleForWeibo()


/**
 * 将时间戳转换成 xx小时前 的样式（同微博）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果在1小时外的今天内，显示今天15:32
 * 如果是昨天的，显示昨天15:32
 * 如果是同一年，显示 09-01 15:32
 * 其余显示，2017-09-01
 */
fun Long.formatAgoStyleForWeibo(): String {
    val now = currentTimeMills
    val span = now - this
    return when {
        span <= TimeUnit.SECONDS.toMillis(1) -> "刚刚"
        span <= TimeUnit.MINUTES.toMillis(1) -> String.format("%d秒前", span / TimeUnit.SECONDS.toMillis(1))
        span <= TimeUnit.HOURS.toMillis(1) -> String.format("%d分钟前", span / TimeUnit.MINUTES.toMillis(1))
        span <= TimeUnit.DAYS.toMillis(1) -> String.format("%d小时前", span / TimeUnit.HOURS.toMillis(1))
        span >= TimeUnit.DAYS.toMillis(1) && span <= TimeUnit.DAYS.toMillis(1) * 2 -> String.format("昨天%tR", this)
        isSameYear(now) -> String.format("%tm-%td %tR", this, this, this)
        else -> String.format("%tF", this)
    }
}


/**
 * 判断两个毫秒值是否在同一年
 */
fun Long.isSameYear(otherMills: Long): Boolean {
    val cal = Calendar.getInstance()
    cal.time = Date(this)
    val cal1 = Calendar.getInstance()
    cal1.time = Date(otherMills)
    return cal[Calendar.YEAR] == cal1[Calendar.YEAR]
}