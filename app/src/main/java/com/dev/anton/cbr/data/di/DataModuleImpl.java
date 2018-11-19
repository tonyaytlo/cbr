package com.dev.anton.cbr.data.di;

import android.content.Context;

import com.dev.anton.cbr.data.cache.CurrencyDbHelper;
import com.dev.anton.cbr.data.cache.CurrencyDbHelperImpl;
import com.dev.anton.cbr.data.model.mapper.CurrencyInfoEntityMapper;
import com.dev.anton.cbr.data.repository.CurrencyRepositoryImpl;
import com.dev.anton.cbr.data.repository.datasource.CurrencyDataStoreFactory;
import com.dev.anton.cbr.domain.repository.CurrencyRepository;

public class DataModuleImpl implements DataModule {

    private Context context;

    public DataModuleImpl(Context context) {
        this.context = context;
    }

    @Override
    public CurrencyDbHelper provideDbHelper() {
        return new CurrencyDbHelperImpl(context);
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
