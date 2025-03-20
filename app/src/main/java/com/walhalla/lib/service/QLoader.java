package com.walhalla.lib.service;

public interface QLoader {
    void showProgressBar();

    void hideProgressBar();

    void handleThrowable(Throwable tr);
}
