package com.dev.anton.cbr.domain.interactors;

import com.dev.anton.cbr.data.model.core.BaseResponse;
import com.dev.anton.cbr.domain.executor.BaseTask;
import com.dev.anton.cbr.domain.executor.BaseTaskFactory;
import com.dev.anton.cbr.domain.model.CurrencyInfo;
import com.dev.anton.cbr.domain.repository.CurrencyRepository;

import java.util.concurrent.Callable;

public class CurrencyUseCase {

    private CurrencyRepository repository;

    public CurrencyUseCase(CurrencyRepository repository) {
        this.repository = repository;
    }

    public BaseTask<CurrencyInfo> fetchCurs() {
        return BaseTaskFactory.create(new Callable<BaseResponse<CurrencyInfo>>() {
            @Override
            public BaseResponse<CurrencyInfo> call() {
                return repository.fetchCurs();
            }
        });
    }
}
