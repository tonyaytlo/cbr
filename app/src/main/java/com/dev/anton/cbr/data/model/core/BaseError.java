package com.dev.anton.cbr.data.model.core;

import java.util.Objects;

public class BaseError {

    private final Exception exception;

    public BaseError(Exception exception) {
        Objects.requireNonNull(exception);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
