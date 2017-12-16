package com.eleven.tocleanmvp;

import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author vic Zhou
 * @time 2017-12-15 23:59
 * <p>
 * Executes asynchronous tasks using a {@link java.util.concurrent.ThreadPoolExecutor}.
 * <p>
 * See also {@link java.util.concurrent.Executors} for a list of factory methods to create common
 * {@link java.util.concurrent.ExecutorService}s for different scenarios.
 * 配置线程池
 */

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {

    private final Handler mHandler = new Handler();

    public static final int POOL_SIZE = 2; // 线程池大小

    public static final int MAX_POOL_SIZE = 4;// 最大线程数

    public static final int TIMEOUT = 30;

    ThreadPoolExecutor mThreadPoolExecutor;

    public UseCaseThreadPoolScheduler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_SIZE));
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <V extends UseCase.ResponseValue> void notifyResponse(final V response, final UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onSucceed(response);
            }
        });
    }

    @Override
    public <V extends UseCase.ResponseValue> void onError(final UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onError();
            }
        });
    }
}
