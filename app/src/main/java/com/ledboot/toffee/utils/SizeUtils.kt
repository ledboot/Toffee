package com.ledboot.toffee.utils

import android.util.DisplayMetrics
import android.util.TypedValue
import com.ledboot.toffee.AppLoader

/**
 * Created by Gwynn on 17/9/25.
 */
object SizeUtils {


    fun dp2px(dpValue: Float): Int {
        val scale: Float = AppLoader.instance.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dp(pxValue: Float): Int {
        val scale: Float = AppLoader.instance.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val fnotScale: Float = AppLoader.instance.resources.displayMetrics.scaledDensity
        return (spValue * fnotScale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        val fontScale: Float = AppLoader.instance.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun applyDimension(unit: Int, value: Float, metrics: DisplayMetrics): Float {
        when (unit) {
            TypedValue.COMPLEX_UNIT_PX -> return value
            TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
            TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
            TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
            TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
            TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
        }
        return 0F
    }
}