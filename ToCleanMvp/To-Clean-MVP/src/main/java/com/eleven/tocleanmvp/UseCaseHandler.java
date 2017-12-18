package com.eleven.tocleanmvp;

import com.eleven.tocleanmvp.util.EspressoIdlingResource;

/**
 * @author vic Zhou
 * @time 2017-12-15 23:20
 * @des Runs {@link UseCase}s using a {@link UseCaseScheduler}.
 */

public class UseCaseHandler {
    private static UseCaseHandler INSTANCE;
    private UseCaseScheduler mUseCaseScheduler;

    public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
        useCase.setRequestValues(values);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

        // The network request might be handled in a different thread so make sure
        // Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mUseCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement();
                }
            }
        });
    }

    public <V extends UseCase.ResponseValue> void notifyResponse(final V response, final UseCase.UseCaseCallback useCaseCallback) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }

    public <V extends UseCase.ResponseValue> void notifyError(final UseCase.UseCaseCallback useCaseCallback) {
        mUseCaseScheduler.onError(useCaseCallback);
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements UseCase.UseCaseCallback<V> {

        private final UseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        public UiCallbackWrapper(UseCase.UseCaseCallback caseCallback, UseCaseHandler useCaseHandler) {
            mCallback = caseCallback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSucceed(V response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError() {
            mUseCaseHandler.notifyError(mCallback);
        }
    }

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return INSTANCE;
    }
}
