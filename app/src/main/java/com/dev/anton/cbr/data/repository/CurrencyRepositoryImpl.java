package com.dev.anton.cbr.data.repository;

import android.support.annotation.NonNull;

import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;
import com.dev.anton.cbr.data.model.mapper.CurrencyInfoEntityMapper;
import com.dev.anton.cbr.data.repository.datasource.CurrencyDataStoreFactory;
import com.dev.anton.cbr.domain.model.CurrencyInfo;
import com.dev.anton.cbr.domain.repository.CurrencyRepository;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final CurrencyInfoEntityMapper currencyInfoEntityMapper;
    private final CurrencyDataStoreFactory currencyDataStoreFactory;


    public CurrencyRepositoryImpl(@NonNull CurrencyInfoEntityMapper currencyInfoEntityMapper,
                                  @NonNull CurrencyDataStoreFactory currencyDataStoreFactory) {
        this.currencyInfoEntityMapper = currencyInfoEntityMapper;
        this.currencyDataStoreFactory = currencyDataStoreFactory;
    }

    @Override
    public BaseResponse<CurrencyInfo> getCurs() {
        return currencyDataStoreFactory.create().getCurrencyInfo().transform(currencyInfoEntityMapper);
    }

    @Override
    public BaseResponse<CurrencyInfo> fetchCurs() {
        BaseResponse<CurrencyInfoEntity> response = currencyDataStoreFactory.createRemote().getCurrencyInfo();
        if (response.isSuccess()) {
            return response.transform(currencyInfoEntityMapper);
        }
        return getCurs();
    }
}
