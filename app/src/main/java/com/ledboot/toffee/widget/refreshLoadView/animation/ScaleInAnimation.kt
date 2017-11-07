package com.ledboot.toffee.widget.refreshLoadView.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View


class ScaleInAnimation : BaseAnimation {

    private var mFrom: Float = DEFAULT_SCALE_FROM

    constructor() : this(DEFAULT_SCALE_FROM)

    constructor(from: Float) {
        mFrom = from
    }

    override fun getAnimators(view: View): Array<Animator> {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f)
        return arrayOf<Animator>(scaleX, scaleY)
    }

    companion object {

        private val DEFAULT_SCALE_FROM = .5f
    }
}
