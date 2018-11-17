package com.dev.anton.cbr.data.net;

import com.dev.anton.cbr.data.entity.ValCursEntity;
import com.dev.anton.cbr.data.entity.base.BaseError;
import com.dev.anton.cbr.data.entity.base.BaseResponse;

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
