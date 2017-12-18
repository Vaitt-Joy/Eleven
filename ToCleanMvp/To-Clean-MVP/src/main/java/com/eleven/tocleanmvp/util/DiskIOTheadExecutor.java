package com.eleven.tocleanmvp.util;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author vic Zhou
 * @time 2017-12-16 0:30
 * @des Executor that runs a task on a new background thread.
 */

public class DiskIOTheadExecutor implements Executor {
    private final Executor mDiskIO;

    public DiskIOTheadExecutor() {
        mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        mDiskIO.execute(runnable);
    }
}
