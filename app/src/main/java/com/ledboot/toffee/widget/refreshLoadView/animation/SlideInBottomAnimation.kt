package com.ledboot.toffee.widget.refreshLoadView.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View



class SlideInBottomAnimation : BaseAnimation {

    constructor()

    override fun getAnimators(view: View): Array<Animator> {
        return arrayOf(ObjectAnimator.ofFloat(view,"translationY", *floatArrayOf(view.measuredHeight.toFloat(),0f)))
    }
}
