package com.dev.anton.cbr.data.model.core;

import android.support.annotation.NonNull;

import java.util.Objects;

public class BaseResponse<T> {

    public final T result;

    public final BaseError error;

    private BaseResponse(@NonNull BaseError error) {
        Objects.requireNonNull(error);
        this.error = error;
        this.result = null;
    }

    private BaseResponse(@NonNull T result) {
        Objects.requireNonNull(result);
        this.result = result;
        this.error = null;
    }

    public static <T> BaseResponse<T> success(@NonNull T result) {
        return new BaseResponse<>(result);
    }

    public static <T> BaseResponse<T> error(@NonNull BaseError error) {
        return new BaseResponse<>(error);
    }

    public boolean isSuccess() {
        return error == null;
    }

    public <OUT> BaseResponse<OUT> transform(@NonNull BaseMapper<T, OUT> mapper) {
        if (this.isSuccess()) {
            return BaseResponse.success(mapper.mapTo(this.result));
        } else {
            return BaseResponse.error(this.error);
        }
    }
}
