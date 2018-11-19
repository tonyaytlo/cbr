package com.dev.anton.cbr.domain.executor;

import com.dev.anton.cbr.data.model.core.BaseResponse;

import java.util.concurrent.Callable;

public class BaseTaskFactory {

    private BaseTaskFactory() {
    }

    public static <V> BaseTask<V> create(final Callable<BaseResponse<V>> call) {
        return new BaseTask<V>() {
            @Override
            BaseResponse<V> async() throws Exception {
                return call.call();
            }
        };
    }
}
