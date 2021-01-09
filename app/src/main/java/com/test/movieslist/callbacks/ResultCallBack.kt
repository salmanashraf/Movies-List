package com.test.movieslist.callbacks

class ResultCallBack<S, E> {
    val complete by lazy { LiveEventData<S>() }
}