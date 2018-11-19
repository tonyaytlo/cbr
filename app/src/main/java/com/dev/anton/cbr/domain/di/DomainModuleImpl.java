package com.dev.anton.cbr.domain.di;

import com.dev.anton.cbr.data.di.DataModule;
import com.dev.anton.cbr.domain.interactors.CurrencyUseCase;
import com.dev.anton.cbr.domain.repository.CurrencyRepository;

public class DomainModuleImpl implements DomainModule {

    private final DataModule dataModule;

    public DomainModuleImpl(DataModule dataModule) {
        this.dataModule = dataModule;
    }

    @Override
    public CurrencyUseCase provideCurrencyUseCase(CurrencyRepository currencyRepository) {
        return new CurrencyUseCase(currencyRepository);
    }

    @Override
    public CurrencyUseCase getCurrencyUseCase() {
        return provideCurrencyUseCase(dataModule.getCursRepository());
    }
}
