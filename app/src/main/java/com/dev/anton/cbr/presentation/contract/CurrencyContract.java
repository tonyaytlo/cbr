package com.dev.anton.cbr.presentation.contract;

import android.content.Context;

import com.dev.anton.cbr.domain.model.CurrencyInfo;
import com.dev.anton.cbr.presentation.presenter.BasePresenter;

public class CurrencyContract {

    public interface View {

        void showError(String msg);

        void showLoading();

        void hideLoading();

        void setCurrency(CurrencyInfo currency);

        void showRetry();

        void hideRetry();

        Context context();
    }

    public interface CurrencyPresenter extends BasePresenter<View> {
        void retry();
    }

}
