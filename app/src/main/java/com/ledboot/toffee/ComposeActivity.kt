package com.ledboot.toffee

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import com.ledboot.toffee.base.BaseActivity

/**
 * Created by Gwynn on 17/10/31.
 */
class ComposeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = FrameLayout(this)
        root.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

        val txt = TextView(this)
        txt.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        root.addView(txt)
        txt.text = "ComposeActivity"

        setContentView(root)
    }
}