package com.ledboot.toffee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ledboot.toffee.base.BaseFrament

/**
 * Created by Eleven on 2017/9/13.
 */
class UserFrament : BaseFrament() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fra_user, container, false)
        return view
    }

    override fun onFirstUserVisible() {
        super.onFirstUserVisible()
    }

    override fun onUserVisible() {
        super.onUserVisible()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }
}