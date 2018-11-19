package com.dev.anton.cbr.data.db;

import com.dev.anton.cbr.data.model.ValCursEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;

public interface DbHelper {

    BaseResponse<ValCursEntity> getCurs();

    void saveCurs(ValCursEntity curs);

    boolean isCached();

    void clearCache();
}
