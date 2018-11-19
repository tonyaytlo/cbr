package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.db.DbHelper;
import com.dev.anton.cbr.data.model.ValCursEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;

class LocalCursDataStore implements CursDataStore {
    private final DbHelper dbHelper;

    LocalCursDataStore(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public BaseResponse<ValCursEntity> getCurs() {
        return dbHelper.getCurs();
    }
}
