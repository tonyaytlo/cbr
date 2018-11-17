package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.db.DbHelper;
import com.dev.anton.cbr.data.entity.ValCursEntity;
import com.dev.anton.cbr.data.entity.base.BaseResponse;
import com.dev.anton.cbr.data.net.CbrApi;

class RemoteCursDataStore implements CursDataStore {

    private final CbrApi cbrApi;
    private final DbHelper dbHelper;

    RemoteCursDataStore(CbrApi cbrApi, DbHelper dbHelper) {
        this.cbrApi = cbrApi;
        this.dbHelper = dbHelper;
    }

    public BaseResponse<ValCursEntity> getCurs() {
        BaseResponse<ValCursEntity> response = cbrApi.getCurs();
        if (response.isSuccess()) {
            dbHelper.saveCurs(response.result);
        }
        return response;
    }
}
