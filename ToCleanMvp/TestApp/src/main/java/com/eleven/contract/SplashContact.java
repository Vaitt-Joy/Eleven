package com.eleven.contract;

import com.eleven.tocleanmvp.BasePresenter;
import com.eleven.tocleanmvp.BaseView;

/**
 * <pre>
 *     author: vic
 *     time  : 17-12-18
 *     desc  : 欢迎界面的契约接口
 * </pre>
 */

public interface SplashContact {
    interface View extends BaseView<Presenter>{

        void showSucceedMessage();

        void showLoad();

        void showError();
    }

    interface Presenter extends BasePresenter{
        void getBaseUrl();

        void getSysInfo();

        void getApiWelcome();
    }
}
