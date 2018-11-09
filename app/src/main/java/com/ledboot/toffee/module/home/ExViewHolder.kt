package com.ledboot.toffee.module.home

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class ExViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(obj: T) {
        binding.setVariable(BR.item, obj)
        binding.executePendingBindings()
    }
}