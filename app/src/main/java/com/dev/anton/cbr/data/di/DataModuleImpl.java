package com.dev.anton.cbr.data.di;

import com.dev.anton.cbr.data.db.CurrencyDbHelper;
import com.dev.anton.cbr.data.db.CurrencyDbHelperImpl;
import com.dev.anton.cbr.data.model.mapper.CurrencyInfoEntityMapper;
import com.dev.anton.cbr.data.repository.CurrencyRepositoryImpl;
import com.dev.anton.cbr.data.repository.datasource.CurrencyDataStoreFactory;
import com.dev.anton.cbr.domain.repository.CurrencyRepository;
import com.dev.anton.cbr.presentation.CbrApp;

public class DataModuleImpl implements DataModule {

    @Override
    public CurrencyDbHelper provideDbHelper() {
        return new CurrencyDbHelperImpl(CbrApp.getAppContext());
    }

    @Override
    public CurrencyDataStoreFactory provideCursDataStoreFactory(CurrencyDbHelper currencyDbHelper) {
        return new CurrencyDataStoreFactory(provideDbHelper());
    }

    @Override
    public CurrencyInfoEntityMapper provideValCursEntityMapper() {
        return new CurrencyInfoEntityMapper();
    }

    @Override
    public CurrencyRepository provideCursRepository(CurrencyInfoEntityMapper currencyInfoEntityMapper,
                                                    CurrencyDataStoreFactory currencyDataStoreFactory) {
        return new CurrencyRepositoryImpl(currencyInfoEntityMapper, currencyDataStoreFactory);
    }

    @Override
    public CurrencyRepository getCursRepository() {
        return provideCursRepository(provideValCursEntityMapper(), provideCursDataStoreFactory(provideDbHelper()));
    }

}
