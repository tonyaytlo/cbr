package com.dev.anton.cbr.domain.di;

import com.dev.anton.cbr.data.di.DataModule;
import com.dev.anton.cbr.domain.interactors.CurrencyUseCase;
import com.dev.anton.cbr.domain.repository.CursRepository;

public class DomainModuleImpl implements DomainModule {

    DataModule dataModule;

    public DomainModuleImpl(DataModule dataModule) {
        this.dataModule = dataModule;
    }

    @Override
    public CurrencyUseCase provideCurrencyUseCase(CursRepository cursRepository) {
        return new CurrencyUseCase(cursRepository);
    }

    @Override
    public CurrencyUseCase getCurrencyUseCase() {
        return provideCurrencyUseCase(dataModule.getCursRepository());
    }
}
