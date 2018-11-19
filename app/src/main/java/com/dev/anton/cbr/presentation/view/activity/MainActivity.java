package com.dev.anton.cbr.presentation.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dev.anton.cbr.R;
import com.dev.anton.cbr.domain.model.CurrencyInfo;
import com.dev.anton.cbr.presentation.contract.CurrencyContract;
import com.dev.anton.cbr.presentation.di.PresentationModuleImpl;
import com.dev.anton.cbr.presentation.presenter.CurrencyPresenter;
import com.dev.anton.cbr.presentation.view.CurrencyConvertView;
import com.dev.anton.cbr.presentation.view.LoadingView;

public class MainActivity extends BaseActivity implements CurrencyContract.View {

    private CurrencyConvertView ccCurrencyConvert;
    private LoadingView lvLoading;
    private Button btnRetry;
    private TextView tvErrorRetry;

    private CurrencyContract.CurrencyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ccCurrencyConvert = findViewById(R.id.ccCurrencyConvert);
        lvLoading = findViewById(R.id.lvLoading);
        btnRetry = findViewById(R.id.btnRetry);
        tvErrorRetry = findViewById(R.id.tvErrorRetry);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.retry();
            }
        });

        presenter = providePresenter();
        presenter.onAttachView(this);
    }

    private CurrencyPresenter providePresenter() {
        return new CurrencyPresenter(PresentationModuleImpl.INSTANCE.getCurrencyUseCase());
    }

    @Override
    public void showError(String msg) {
        showToastError(msg);
    }

    @Override
    public void showLoading() {
        lvLoading.showLoading();
    }

    @Override
    public void hideLoading() {
        lvLoading.hideLoading();
    }

    @Override
    public void setCurrency(CurrencyInfo currency) {
        ccCurrencyConvert.populate(currency);
    }

    @Override
    public void showRetry() {
        btnRetry.setVisibility(View.VISIBLE);
        tvErrorRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        btnRetry.setVisibility(View.GONE);
        tvErrorRetry.setVisibility(View.GONE);
    }

    @Override
    public Context context() {
        return this.getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetachView();
    }
}
