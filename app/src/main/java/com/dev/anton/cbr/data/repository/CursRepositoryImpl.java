package com.dev.anton.cbr.data.repository;

import com.dev.anton.cbr.data.model.ValCursEntity;
import com.dev.anton.cbr.data.model.core.BaseResponse;
import com.dev.anton.cbr.data.model.mapper.ValCursEntityMapper;
import com.dev.anton.cbr.data.repository.datasource.CursDataStoreFactory;
import com.dev.anton.cbr.domain.model.ValCurs;
import com.dev.anton.cbr.domain.repository.CursRepository;

public class CursRepositoryImpl implements CursRepository {

    private final ValCursEntityMapper valCursEntityMapper;
    private final CursDataStoreFactory cursDataStoreFactory;


    public CursRepositoryImpl(ValCursEntityMapper valCursEntityMapper,
                              CursDataStoreFactory cursDataStoreFactory) {
        this.valCursEntityMapper = valCursEntityMapper;
        this.cursDataStoreFactory = cursDataStoreFactory;
    }

    @Override
    public BaseResponse<ValCurs> getCurs() {
        return cursDataStoreFactory.create().getCurs().transform(valCursEntityMapper);
    }

    @Override
    public BaseResponse<ValCurs> fetchCurs() {
        BaseResponse<ValCursEntity> response = cursDataStoreFactory.createRemote().getCurs();
        if (response.isSuccess()) {
            return response.transform(valCursEntityMapper);
        }
        return getCurs();//if error try to read from cache
    }
}
