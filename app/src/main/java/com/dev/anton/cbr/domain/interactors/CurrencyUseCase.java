package com.dev.anton.cbr.domain.interactors;

import com.dev.anton.cbr.data.model.core.BaseResponse;
import com.dev.anton.cbr.domain.executor.BaseTask;
import com.dev.anton.cbr.domain.executor.BaseTaskFactory;
import com.dev.anton.cbr.domain.model.ValCurs;
import com.dev.anton.cbr.domain.repository.CursRepository;

import java.util.concurrent.Callable;

public class CurrencyUseCase implements UseCase {

    private CursRepository repository;

    public CurrencyUseCase(CursRepository repository) {
        this.repository = repository;
    }

    public BaseTask<ValCurs> fetchCurs() {
        return BaseTaskFactory.create(new Callable<BaseResponse<ValCurs>>() {
            @Override
            public BaseResponse<ValCurs> call() {
                return repository.fetchCurs();
            }
        });
    }
}
