package com.eleven.tocleanmvp;

/**
 * @param <Q> the request type
 * @param <P> the response type
 * @author vic Zhou
 * @time 2017-12-15 23:13
 * @des * Use cases are the entry points to the domain layer.
 */

public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValue> {

    private Q mRequestValues;

    private UseCaseCallback<P> mUseCaseCallback;

    public Q getRequestValues() {
        return mRequestValues;
    }

    public void setRequestValues(Q mRequestValues) {
        this.mRequestValues = mRequestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> mUseCaseCallback) {
        this.mUseCaseCallback = mUseCaseCallback;
    }

    void run() {
        executeUseCase(mRequestValues);
    }

    protected abstract void executeUseCase(Q mRequestValues);

    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received form a request
     */
    public interface ResponseValue {
    }

    public interface UseCaseCallback<R> {
        void onSucceed(R response);

        void onError();
    }
}
