package com.example.gokart

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.waitAndGet() : T{

    val waitTime = 2L
    var data : T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(it: T?) {
            data = it
            latch.countDown()
            this@waitAndGet.removeObserver(this)
        }
    }

    this.observeForever(observer)

    if(!latch.await(waitTime, TimeUnit.SECONDS )){
        this.removeObserver(observer)
        throw TimeoutException("ObserverTimeout")
    }

    return data as T

}