package com.dev.anton.cbr.domain.di;

import com.dev.anton.cbr.domain.interactors.CurrencyUseCase;
import com.dev.anton.cbr.domain.repository.CursRepository;

public interface DomainModule {

    CurrencyUseCase provideCurrencyUseCase(CursRepository cursRepository);

    CurrencyUseCase getCurrencyUseCase();

}
