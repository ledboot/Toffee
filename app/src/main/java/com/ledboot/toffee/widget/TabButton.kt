package com.ledboot.toffee.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by Gwynn on 17/9/20.
 */
class TabButton : LinearLayout {

    private var icon: ImageView? = null
    private var text: TextView? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        icon = ImageView(context)
        text = TextView(context)
    }

}