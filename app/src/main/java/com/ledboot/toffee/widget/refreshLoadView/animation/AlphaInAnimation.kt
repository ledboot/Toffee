package com.ledboot.toffee.widget.refreshLoadView.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View


class AlphaInAnimation : BaseAnimation {

    private var mFrom: Float = DEFAULT_ALPHA_FROM

    constructor() : this(DEFAULT_ALPHA_FROM)

    constructor(from: Float) {
        mFrom = from
    }

    override fun getAnimators(view: View): Array<Animator> {
        return arrayOf(ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f))
    }

    companion object {
        private val DEFAULT_ALPHA_FROM = 0f
    }
}
