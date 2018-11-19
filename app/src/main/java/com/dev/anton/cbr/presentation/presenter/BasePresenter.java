package com.dev.anton.cbr.presentation.presenter;

public interface BasePresenter<V> {

    void onAttachView(V view);

    void onDetachView();
}
