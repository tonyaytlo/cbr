package com.dev.anton.cbr.domain.model.core;

import com.dev.anton.cbr.domain.executor.Task;

import java.util.HashSet;

public class CompositeTask {

    private final HashSet<Task> tasks = new HashSet<>(2);

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void cancelAll() {
        for (Task task : tasks) {
            task.cancelTask();
        }
    }
}
