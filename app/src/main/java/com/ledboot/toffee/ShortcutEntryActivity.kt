package com.ledboot.toffee

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import com.ledboot.toffee.base.BaseActivity

/**
 * Created by Gwynn on 17/10/30.
 */
class ShortcutEntryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        if (intent == null)
            finish()
        if (intent.action == null || !intent.action.startsWith("com.ledboot.shortentry")) {
            finish()
            return
        }
        val userId = intent.getIntExtra("userId",0)
        val root = FrameLayout(this)
        root.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)

        val txt =TextView(this)
        txt.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT)
        root.addView(txt)
        txt.text = "userId = $userId"

        setContentView(root)
    }
}