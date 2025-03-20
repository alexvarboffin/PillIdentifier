package com.walhalla.pillfinder.ui.helper

interface NavigatorView {
    fun reset()

    companion object {
        const val PAGE_START_INDEX: Int = 1 //We start from number 1, this number from API json
    }
}
