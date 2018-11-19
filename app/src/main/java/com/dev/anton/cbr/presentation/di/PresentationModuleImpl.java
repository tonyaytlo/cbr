package com.dev.anton.cbr.presentation.di;

import com.dev.anton.cbr.domain.di.DomainModuleImpl;
import com.dev.anton.cbr.domain.interactors.CurrencyUseCase;

public class PresentationModuleImpl implements PresentationModule {

    private DomainModuleImpl domainModuleImpl;

    public static PresentationModuleImpl INSTANCE;

    private PresentationModuleImpl(DomainModuleImpl domainModuleImpl) {
        this.domainModuleImpl = domainModuleImpl;
    }

    public static void build(DomainModuleImpl domainModuleImpl) {
        if (INSTANCE == null) {
            synchronized (PresentationModuleImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PresentationModuleImpl(domainModuleImpl);
                }
            }
        }
    }

    @Override
    public CurrencyUseCase getCurrencyUseCase() {
        return domainModuleImpl.getCurrencyUseCase();
    }
}
