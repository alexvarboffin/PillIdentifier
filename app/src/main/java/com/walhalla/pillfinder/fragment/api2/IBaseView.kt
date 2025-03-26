package com.walhalla.pillfinder.fragment.api2


interface IBaseView<T> : ILoadingView {
    fun updateData(data: T)

    fun showError(error: String)

    fun showSuccess(success: String)
}
