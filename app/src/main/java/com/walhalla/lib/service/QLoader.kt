package com.walhalla.lib.service

interface QLoader {
    fun showProgressBar()

    fun hideProgressBar()

    fun handleThrowable(tr: Throwable)
}
