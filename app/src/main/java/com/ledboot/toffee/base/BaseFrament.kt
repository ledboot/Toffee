package com.ledboot.toffee.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.utils.Debuger
import dagger.android.support.DaggerFragment

/**
 * Created by Gwynn on 17/8/31.
 */

open class BaseFrament : DaggerFragment() {

    private var TAG: String = BaseFrament::class.java.simpleName
    private var isFirstVisible: Boolean = true
    private var isFirstInvisible: Boolean = true
    private var isPrepared: Boolean = false
    private var isFirstResume = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Debuger.logD(TAG, "onActivityCreated,class =${this.javaClass.simpleName}")
        super.onActivityCreated(savedInstanceState)
        initPrepare()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Debuger.logD(TAG, "onCreate, ${this.javaClass.simpleName}")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Debuger.logD(TAG, "onCreateView, ${this.javaClass.simpleName}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Debuger.logD(TAG, "setUserVisibleHint,isVisibleToUser=${isVisibleToUser}, class =${this.javaClass.simpleName}")
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
        Debuger.logD(TAG, "onFirstUserVisible,class =${this.javaClass.simpleName}")
    }

    open protected fun onFirstUserInvisible() {

    }

    open protected fun onUserVisible() {
        Debuger.logD(TAG, "onUserVisible,class =${this.javaClass.simpleName}")

    }

    open protected fun onUserInvisible() {

    }
}