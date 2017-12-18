package com.eleven.tocleanmvp;

/**
 * @author vic Zhou
 * @time 2017-12-15 23:21
 * @des Interface for schedulers, see {@link UseCaseThreadPoolScheduler}.
 */

public interface UseCaseScheduler {
    void execute(Runnable runnable);

    <V extends UseCase.ResponseValue> void notifyResponse(final V response, final UseCase.UseCaseCallback<V> useCaseCallback);

    <V extends UseCase.ResponseValue> void onError(final UseCase.UseCaseCallback<V> useCaseCallback);
}
