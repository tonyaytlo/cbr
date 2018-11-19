package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.model.ValCursEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;

public interface CursDataStore {
    BaseResponse<ValCursEntity> getCurs();
}
