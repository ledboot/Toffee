package com.ledboot.toffee.data

import androidx.lifecycle.MediatorLiveData

abstract class MediatorCase<in P, R> {

    protected val result = MediatorLiveData<Result<R>>()

    open fun observe(): MediatorLiveData<Result<R>> {
        return result
    }

    abstract fun execute(parameters: P)
}