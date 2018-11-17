package com.dev.anton.cbr.data.entity.base;

public abstract class BaseMapper<IN, OUT> {

    abstract public OUT mapTo(IN in);
}
