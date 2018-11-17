package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.db.DbHelper;
import com.dev.anton.cbr.data.net.CbrApiImpl;

public class CursDataStoreFactory {

    private DbHelper dbHelper;

    CursDataStoreFactory(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public CursDataStore create() {
        return new LocalCursDataStore(dbHelper);
    }

    public CursDataStore createRemote() {
        return new RemoteCursDataStore(new CbrApiImpl(), dbHelper);
    }
}
