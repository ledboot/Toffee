package com.ledboot.toffee.base

import android.os.Bundle
import com.ledboot.toffee.di.androidx.DaggerAppCompatActivityX

/**
 * Created by Gwynn on 17/8/31.
 */

open class BaseActivity : DaggerAppCompatActivityX() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}