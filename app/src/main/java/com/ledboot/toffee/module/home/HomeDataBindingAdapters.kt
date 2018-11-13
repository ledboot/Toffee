package com.ledboot.toffee.module.home

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ledboot.toffee.utils.DefaultDateFormat
import com.ledboot.toffee.utils.formatAgoStyleForWeibo

@BindingAdapter("authorImage")
fun authorAvator(imageView: ImageView, url: String) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView).load(url).into(imageView)
    }
}

@BindingAdapter("formatDate")
fun formatDate(textview: TextView, date: String) {
    textview.text = formatAgoStyleForWeibo(date, DefaultDateFormat.DEFAULT_FORMAT2.get())
}

