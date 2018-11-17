package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.entity.ValCursEntity;
import com.dev.anton.cbr.data.entity.base.BaseResponse;

public interface CursDataStore {
    BaseResponse<ValCursEntity> getCurs();
}
