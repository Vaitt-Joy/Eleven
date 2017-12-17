package com.eleven.tomvp.mvp;

/**
 * @author vic Zhou
 * @time 2017-12-17 21:25
 * @des ${TODO}
 */

public class BasePresenter<M extends IModel, V extends BaseView> implements Presenter {

    protected final String TAG = this.getClass().getSimpleName();
//    protected CompositeSubscription mCompositeSubscription;

    protected M mModel;
    protected V mRootView;


    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        if (mModel != null) {
            mModel.onDestroy();
            this.mModel = null;
        }
        this.mRootView = null;
    }
}
