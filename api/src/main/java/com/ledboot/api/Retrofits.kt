package com.ledboot.api

/**
 * Created by Gwynn on 17/9/5.
 */


class Retrofits private constructor(){

    companion object {
        val instance:Retrofits by lazy(LazyThreadSafetyMode.PUBLICATION) { Retrofits() }
    }
}