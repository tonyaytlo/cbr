package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;

public interface CurrencyDataStore {
    BaseResponse<CurrencyInfoEntity> getCurrencyInfo();
}
