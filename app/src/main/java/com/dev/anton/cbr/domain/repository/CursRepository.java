package com.dev.anton.cbr.domain.repository;

import com.dev.anton.cbr.data.model.core.BaseResponse;
import com.dev.anton.cbr.domain.model.ValCurs;

public interface CursRepository {

    BaseResponse<ValCurs> getCurs();

    BaseResponse<ValCurs> fetchCurs();
}
