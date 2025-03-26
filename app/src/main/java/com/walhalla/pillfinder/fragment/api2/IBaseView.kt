package com.walhalla.pillfinder.fragment.api2;



public interface IBaseView<T> extends ILoadingView {
    void updateData(T data);

    void showError(String error);

    void showSuccess(String success);
}
