package com.ledboot.toffee.widget.refreshLoadView.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View



class SlideInLeftAnimation : BaseAnimation {


    override fun getAnimators(view: View): Array<Animator> {
        return arrayOf(ObjectAnimator.ofFloat(view, "translationX", *floatArrayOf(-view.rootView.width.toFloat(), 0f)))
    }
}
