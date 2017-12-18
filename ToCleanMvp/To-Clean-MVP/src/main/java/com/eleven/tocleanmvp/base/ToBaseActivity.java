package com.eleven.tocleanmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author vic Zhou
 * @time 2017-12-17 20:52
 * @des 基类
 */

public abstract class ToBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayoutId());
        initView(savedInstanceState);
        initDate();
        initEvent();
    }

    protected abstract int bindLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected void initDate() {

    }

    protected void initEvent() {

    }

}
