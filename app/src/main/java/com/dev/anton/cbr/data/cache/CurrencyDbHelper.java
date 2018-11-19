package com.dev.anton.cbr.data.cache;

import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;

public interface CurrencyDbHelper {

    BaseResponse<CurrencyInfoEntity> getCurrencyInfo();

    void saveCurrencyInfo(CurrencyInfoEntity curs);

    boolean isCached();

    void clearCache();
}
