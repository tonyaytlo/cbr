package com.dev.anton.cbr.data.net;

import com.dev.anton.cbr.data.model.ValCursEntity;
import com.dev.anton.cbr.data.model.core.BaseError;
import com.dev.anton.cbr.data.model.core.BaseResponse;

import java.net.MalformedURLException;

public class CbrApiImpl implements CbrApi {

    @Override
    public BaseResponse<ValCursEntity> getCurs() {
        try {
            return ApiConnection.createGET(GET_CURS, ValCursEntity.class).requestSyncCall();
        } catch (MalformedURLException e) {
            return BaseResponse.error(new BaseError(e));
        }
    }
}
