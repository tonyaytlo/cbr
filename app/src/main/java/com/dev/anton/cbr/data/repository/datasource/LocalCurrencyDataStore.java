package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.cache.CurrencyDbHelper;
import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;

class LocalCurrencyDataStore implements CurrencyDataStore {
    private final CurrencyDbHelper currencyDbHelper;

    LocalCurrencyDataStore(CurrencyDbHelper currencyDbHelper) {
        this.currencyDbHelper = currencyDbHelper;
    }

    public BaseResponse<CurrencyInfoEntity> getCurrencyInfo() {
        return currencyDbHelper.getCurrencyInfo();
    }
}
