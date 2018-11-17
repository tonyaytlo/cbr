package com.dev.anton.cbr.data.entity.base;

public class BaseError {

    private final Exception exception;

    public BaseError(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
