package com.dev.anton.cbr.presentation.presenter;

import com.dev.anton.cbr.domain.executor.BaseTask;
import com.dev.anton.cbr.domain.executor.Task;
import com.dev.anton.cbr.domain.interactors.CurrencyUseCase;
import com.dev.anton.cbr.domain.model.CurrencyInfo;
import com.dev.anton.cbr.presentation.contract.CurrencyContract;
import com.dev.anton.cbr.presentation.exception.ErrorMsgFactory;

public class CurrencyPresenter implements CurrencyContract.CurrencyPresenter {

    private final CurrencyUseCase currencyUseCase;
    private CurrencyContract.View view;

    private Task task;

    public CurrencyPresenter(CurrencyUseCase currencyUseCase) {
        this.currencyUseCase = currencyUseCase;
    }

    @Override
    public void retry() {
        view.hideRetry();
        loadData();
    }

    private void loadData() {
        view.showLoading();
        task = currencyUseCase.fetchCurs()
                .setOnCompleteListener(new BaseTask.OnCompleteListener<CurrencyInfo>() {
                    @Override
                    public void onSuccess(CurrencyInfo result) {
                        view.hideLoading();
                        view.setCurrency(result);
                    }

                    @Override
                    public void onError(Exception error) {
                        view.hideLoading();
                        view.showRetry();
                        view.showError(ErrorMsgFactory.create(view.context(), error));
                    }
                });
        task.executeAsync();
    }

    @Override
    public void onAttachView(CurrencyContract.View view) {
        this.view = view;
        loadData();
    }

    @Override
    public void onDetachView() {
        if (task != null) {
            task.cancelTask();
        }
    }
}
