package com.ledboot.toffee.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.utils.Debuger

/**
 * Created by Gwynn on 17/8/31.
 */

open class BaseFrament : Fragment() {

    private var TAG: String = BaseFrament::class.java.simpleName
    private var isFirstVisible: Boolean = true
    private var isFirstInvisible: Boolean = true
    private var isPrepared: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Debuger.logD(TAG,"onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Debuger.logD(TAG,"onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Debuger.logD(TAG,"setUserVisibleHint")
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
        Debuger.logD(TAG, "onFirstUserVisible")
    }

    open protected fun onFirstUserInvisible() {

    }

    open protected fun onUserVisible() {
        Debuger.logD(TAG, "onUserVisible")

    }

    open protected fun onUserInvisible() {

    }
}