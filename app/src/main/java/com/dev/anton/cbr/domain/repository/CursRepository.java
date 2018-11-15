package com.dev.anton.cbr.domain.repository;

import com.dev.anton.cbr.data.entity.base.BaseResponse;
import com.dev.anton.cbr.domain.model.ValCurs;

public interface CursRepository {

    BaseResponse<ValCurs> getCurs();
}
