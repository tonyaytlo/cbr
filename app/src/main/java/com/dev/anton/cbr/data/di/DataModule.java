package com.dev.anton.cbr.data.di;

import com.dev.anton.cbr.data.db.DbHelper;
import com.dev.anton.cbr.data.model.mapper.ValCursEntityMapper;
import com.dev.anton.cbr.data.net.CbrApi;
import com.dev.anton.cbr.data.repository.datasource.CursDataStoreFactory;
import com.dev.anton.cbr.domain.repository.CursRepository;

public interface DataModule {

    DbHelper provideDbHelper();

    CbrApi provideCbrApi();

    CursDataStoreFactory provideCursDataStoreFactory(DbHelper dbHelper);

    ValCursEntityMapper provideValCursEntityMapper();

    CursRepository provideCursRepository(ValCursEntityMapper valCursEntityMapper,
                                         CursDataStoreFactory cursDataStoreFactory);

    CursRepository getCursRepository();
}
