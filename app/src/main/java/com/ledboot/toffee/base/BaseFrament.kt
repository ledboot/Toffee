package com.ledboot.toffee.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Gwynn on 17/8/31.
 */

open class BaseFrament : Fragment() {

    private var isFirstVisible: Boolean = true
    private var isFirstInvisible: Boolean = true
    private var isPrepared: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstVisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    protected fun initPrepare() {
        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }

    open protected fun onFirstUserVisible() {

    }

    open protected fun onFirstUserInvisible() {

    }

    open protected fun onUserVisible() {

    }

    open protected fun onUserInvisible() {

    }
}