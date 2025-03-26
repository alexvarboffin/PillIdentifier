package com.walhalla.pillfinder.activity;

public interface IPresenter {

    enum QEvent {
        ADD,
        REPLACE
    }

    void loadNextPageRequest(int pageNumber, QEvent event);

    void onDestroy();

    interface PresenterCallback {

        void hideRefreshLayoutProgress();

        void hideProgressBar();

        void showProgressBar();

        void handleThrowable(Throwable throwable);
    }
}
