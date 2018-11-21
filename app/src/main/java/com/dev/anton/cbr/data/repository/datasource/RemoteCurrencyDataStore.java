package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.cache.CurrencyDbHelper;
import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;
import com.dev.anton.cbr.data.net.CbrApi;

class RemoteCurrencyDataStore implements CurrencyDataStore {

    private final CbrApi cbrApi;
    private final CurrencyDbHelper currencyDbHelper;

    RemoteCurrencyDataStore(CbrApi cbrApi, CurrencyDbHelper currencyDbHelper) {
        this.cbrApi = cbrApi;
        this.currencyDbHelper = currencyDbHelper;
    }

    public BaseResponse<CurrencyInfoEntity> getCurrencyInfo() {
        BaseResponse<CurrencyInfoEntity> response = cbrApi.getCurrencyInfo();
        if (response.isSuccess()) {
            currencyDbHelper.saveCurrencyInfo(response.result);
        }
        return response;
    }
}
