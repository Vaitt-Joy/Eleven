package com.eleven.presenter;

import android.support.annotation.NonNull;

import com.eleven.contract.SplashContact;
import com.eleven.tocleanmvp.UseCaseHandler;

import static com.eleven.lib.commenlib.utils.NullUtils.checkNotNull;

/**
 * @author vic Zhou
 * @time 2017-12-18 22:57
 * @des splash P
 */

public class SplashPresenter implements SplashContact.Presenter {

    private final SplashContact.View mSplashView;
    private final UseCaseHandler mUseCaseHandler;

    public SplashPresenter(
            @NonNull UseCaseHandler useCaseHandler,
            @NonNull SplashContact.View statisticsView) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler cannot be null!");
        mSplashView = checkNotNull(statisticsView, "StatisticsView cannot be null!");

        mSplashView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getBaseUrl() {

    }

    @Override
    public void getSysInfo() {

    }

    @Override
    public void getApiWelcome() {

    }
}
