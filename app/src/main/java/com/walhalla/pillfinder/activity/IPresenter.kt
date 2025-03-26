package com.walhalla.pillfinder.activity

interface IPresenter {

    enum class QEvent {
        ADD,
        REPLACE
    }

    fun loadNextPageRequest(pageNumber: Int, event: QEvent)

    fun onDestroy()

    interface PresenterCallback {
        fun hideRefreshLayoutProgress()

        fun hideProgressBar()

        fun showProgressBar()

        fun handleThrowable(throwable: Throwable)
    }
}
