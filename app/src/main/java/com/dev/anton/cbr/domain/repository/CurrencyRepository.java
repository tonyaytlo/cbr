package com.dev.anton.cbr.domain.repository;

import com.dev.anton.cbr.data.model.core.BaseResponse;
import com.dev.anton.cbr.domain.model.CurrencyInfo;

public interface CurrencyRepository {

    BaseResponse<CurrencyInfo> getCurs();

    BaseResponse<CurrencyInfo> fetchCurs();
}
