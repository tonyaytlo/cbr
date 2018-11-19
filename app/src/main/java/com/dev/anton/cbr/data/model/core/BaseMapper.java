package com.dev.anton.cbr.data.model.core;

public abstract class BaseMapper<IN, OUT> {

    abstract public OUT mapTo(IN in);
}
