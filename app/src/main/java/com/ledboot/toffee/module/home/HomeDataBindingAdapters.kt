package com.ledboot.toffee.module.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("authorImage")
fun authorAvator(imageView: ImageView, url: String) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView).load(url).into(imageView)
    }
}