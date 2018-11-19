package com.dev.anton.cbr.data.di;

import com.dev.anton.cbr.data.cache.CurrencyDbHelper;
import com.dev.anton.cbr.data.model.mapper.CurrencyInfoEntityMapper;
import com.dev.anton.cbr.data.repository.datasource.CurrencyDataStoreFactory;
import com.dev.anton.cbr.domain.repository.CurrencyRepository;

public interface DataModule {

    CurrencyDbHelper provideDbHelper();

    CurrencyDataStoreFactory provideCursDataStoreFactory(CurrencyDbHelper currencyDbHelper);

    CurrencyInfoEntityMapper provideValCursEntityMapper();

    CurrencyRepository provideCursRepository(CurrencyInfoEntityMapper currencyInfoEntityMapper,
                                             CurrencyDataStoreFactory currencyDataStoreFactory);

    CurrencyRepository getCursRepository();
}
