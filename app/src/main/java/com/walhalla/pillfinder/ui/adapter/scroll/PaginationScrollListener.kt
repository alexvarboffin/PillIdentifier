package com.walhalla.pillfinder.ui.adapter.scroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    private var isLoaded = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (isLoaded) {
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.getItemCount()
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!this.isLoading && !this.isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                    totalItemCount && firstVisibleItemPosition >= 0
                ) {
                    loadMoreItems()
                }
            }
        }
        isLoaded = true
    }

    protected abstract fun loadMoreItems()

    abstract val totalPageCount: Int

    abstract val isLastPage: Boolean

    abstract val isLoading: Boolean
}
