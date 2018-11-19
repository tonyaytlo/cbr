package com.dev.anton.cbr.domain.di;

import com.dev.anton.cbr.domain.interactors.CurrencyUseCase;
import com.dev.anton.cbr.domain.repository.CurrencyRepository;

public interface DomainModule {

    CurrencyUseCase provideCurrencyUseCase(CurrencyRepository currencyRepository);

    CurrencyUseCase getCurrencyUseCase();

}
