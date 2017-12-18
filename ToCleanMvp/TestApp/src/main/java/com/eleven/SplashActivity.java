package com.eleven;

import android.os.Bundle;

import com.eleven.contract.SplashContact;
import com.eleven.lib.commenlib.utils.NullUtils;
import com.eleven.lib.commenlib.utils.ToastUtils;
import com.eleven.lib.commenlib.utils.ToolUI;
import com.eleven.tocleanmvp.base.ToBaseActivity;

public class SplashActivity extends ToBaseActivity implements SplashContact.View {

    private SplashContact.Presenter mPresenter;

    @Override
    public int bindLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initDate() {
        // 获取 Uri
        String GET_HOST_HEADER = String.format(ToolUI.getString(R.string.rootUrl) + ToolUI.getString(R.string.secendUrl), ToolUI.getString(R.string.website), ToolUI.getString(R.string.number));
        ToastUtils.showShort(GET_HOST_HEADER);
    }

    @Override
    public void setPresenter(SplashContact.Presenter presenter) {
        mPresenter = NullUtils.checkNotNull(presenter);
    }

    @Override
    public void showSucceedMessage() {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showError() {

    }
}
