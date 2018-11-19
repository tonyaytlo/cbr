package com.dev.anton.cbr.domain.executor;

public interface Task {

    void executeAsync();

    void cancelTask();
}
