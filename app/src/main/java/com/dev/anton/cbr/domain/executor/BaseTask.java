package com.dev.anton.cbr.domain.executor;

import android.os.AsyncTask;

import com.dev.anton.cbr.data.model.core.BaseError;
import com.dev.anton.cbr.data.model.core.BaseResponse;

public abstract class BaseTask<V> extends AsyncTask<Object, Void, BaseResponse<V>> implements Task {

    private OnCompleteListener<V> onCompleteListener;

    @Override
    protected BaseResponse<V> doInBackground(Object... objects) {
        try {
            return async();
        } catch (Exception e) {
            return BaseResponse.error(new BaseError(e));
        }
    }

    abstract BaseResponse<V> async() throws Exception;

    @Override
    protected void onPostExecute(BaseResponse<V> result) {
        if (!isCancelled() && onCompleteListener != null) {
            if (result.isSuccess()) {
                onCompleteListener.onSuccess(result.result);
            } else {
                onCompleteListener.onError(result.error.getException());
            }
        }
    }

    @Override
    public void executeAsync() {
        this.execute();
    }

    @Override
    public void cancelTask() {
        this.cancel(false);
        this.onCompleteListener = null;
    }

    public final Task setOnCompleteListener(final OnCompleteListener<V> listener) {
        this.onCompleteListener = listener;
        return this;
    }

    public interface OnCompleteListener<V> {
        void onSuccess(V result);

        void onError(Exception error);
    }
}
