package com.ledboot.toffee.config

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.GlideModule

/**
 * Created by Gwynn on 17/10/23.
 */
class GlideConfig : GlideModule {


    override fun registerComponents(context: Context?, glide: Glide?) {
    }


    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        builder!!.setDiskCache(InternalCacheDiskCacheFactory(context, "glide_cache", 100 * 1024 * 1024))
    }
}