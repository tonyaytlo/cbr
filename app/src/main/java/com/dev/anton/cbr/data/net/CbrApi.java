package com.dev.anton.cbr.data.net;

import com.dev.anton.cbr.data.model.CurrencyInfoEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;

public interface CbrApi {

    String API_BASE_URL = "http://www.cbr.ru/";
    String GET_CURRENCY_INFO = API_BASE_URL + "scripts/XML_daily.asp/";

    BaseResponse<CurrencyInfoEntity> getCurrencyInfo();
}
