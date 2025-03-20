package com.walhalla.pillfinder.fragment.main;

import androidx.annotation.StringRes;

public interface IMainView {

    void mSnackbar(@StringRes int message);

    void mSnackbar(String s);

    void hideProgressBar();

    void showProgressBar();
}
