package com.dev.anton.cbr.data.repository.datasource;

import com.dev.anton.cbr.data.cache.CurrencyDbHelper;
import com.dev.anton.cbr.data.net.CbrApiImpl;

public class CurrencyDataStoreFactory {

    private CurrencyDbHelper currencyDbHelper;

    public CurrencyDataStoreFactory(CurrencyDbHelper currencyDbHelper) {
        this.currencyDbHelper = currencyDbHelper;
    }

    public CurrencyDataStore create() {
        return new LocalCurrencyDataStore(currencyDbHelper);
    }

    public CurrencyDataStore createRemote() {
        return new RemoteCurrencyDataStore(new CbrApiImpl(), currencyDbHelper);
    }
}
