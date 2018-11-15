package com.dev.anton.cbr.data.db;

import com.dev.anton.cbr.data.entity.ValCursEntity;
import com.dev.anton.cbr.data.entity.base.BaseResponse;

public interface DbHelper {

    BaseResponse<ValCursEntity> getCurs();

    void saveCurs(ValCursEntity curs);
}
