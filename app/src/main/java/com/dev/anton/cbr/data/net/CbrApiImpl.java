package com.dev.anton.cbr.data.net;

import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseError;
import com.dev.anton.cbr.data.model.core.BaseResponse;

import java.net.MalformedURLException;

public class CbrApiImpl implements CbrApi {

    @Override
    public BaseResponse<CurrencyInfoEntity> getCurrencyInfo() {
        try {
            return ApiConnection.createGET(GET_CURRENCY_INFO, CurrencyInfoEntity.class).requestSyncCall();
        } catch (MalformedURLException e) {
            return BaseResponse.error(new BaseError(e));
        }
    }
}
