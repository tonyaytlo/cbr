package com.dev.anton.cbr.data.model.core;

import android.support.annotation.Nullable;

public abstract class BaseMapper<IN, OUT> {

    abstract public OUT mapTo(@Nullable IN in);
}
