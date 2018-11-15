package com.dev.anton.cbr.data.entity.base;

public class BaseResponse<T> {

    public final T result;

    public final BaseError error;

    private BaseResponse(BaseError error) {
        this.result = null;
        this.error = error;
    }

    private BaseResponse(T result) {
        this.result = result;
        this.error = null;
    }

    public static <T> BaseResponse<T> success(T result) {
        return new BaseResponse<>(result);
    }


    public static <T> BaseResponse<T> error(BaseError error) {
        return new BaseResponse<>(error);
    }

    public boolean isSuccess() {
        return error == null;
    }
}
