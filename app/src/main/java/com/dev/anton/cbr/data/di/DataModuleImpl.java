package com.dev.anton.cbr.data.di;

import com.dev.anton.cbr.data.db.CursDbHelper;
import com.dev.anton.cbr.data.db.DbHelper;
import com.dev.anton.cbr.data.model.mapper.ValCursEntityMapper;
import com.dev.anton.cbr.data.net.CbrApi;
import com.dev.anton.cbr.data.net.CbrApiImpl;
import com.dev.anton.cbr.data.repository.CursRepositoryImpl;
import com.dev.anton.cbr.data.repository.datasource.CursDataStoreFactory;
import com.dev.anton.cbr.domain.repository.CursRepository;
import com.dev.anton.cbr.presentation.CbrApp;

public class DataModuleImpl implements DataModule {


    @Override
    public DbHelper provideDbHelper() {
        return new CursDbHelper(CbrApp.getAppContext());
    }

    @Override
    public CbrApi provideCbrApi() {
        return new CbrApiImpl();
    }

    @Override
    public CursDataStoreFactory provideCursDataStoreFactory(DbHelper dbHelper) {
        return new CursDataStoreFactory(provideDbHelper());
    }

    @Override
    public ValCursEntityMapper provideValCursEntityMapper() {
        return new ValCursEntityMapper();
    }

    @Override
    public CursRepository provideCursRepository(ValCursEntityMapper valCursEntityMapper,
                                                CursDataStoreFactory cursDataStoreFactory) {
        return new CursRepositoryImpl(valCursEntityMapper, cursDataStoreFactory);
    }

    @Override
    public CursRepository getCursRepository() {
        return provideCursRepository(provideValCursEntityMapper(), provideCursDataStoreFactory(provideDbHelper()));
    }


}
