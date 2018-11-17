package com.dev.anton.cbr.data.entity.base;

import java.util.Objects;

public class BaseResponse<T> {

    public final T result;

    public final BaseError error;

    private BaseResponse(BaseError error) {
        Objects.requireNonNull(error);
        this.error = error;
        this.result = null;
    }

    private BaseResponse(T result) {
        Objects.requireNonNull(result);
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

    public <OUT> BaseResponse<OUT> transform(BaseMapper<T, OUT> mapper) {
        if (this.isSuccess()) {
            return BaseResponse.success(mapper.mapTo(this.result));
        } else {
            return BaseResponse.error(this.error);
        }
    }
}
