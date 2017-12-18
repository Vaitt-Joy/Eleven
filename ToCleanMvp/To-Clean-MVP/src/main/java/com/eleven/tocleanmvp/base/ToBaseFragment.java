package com.eleven.tocleanmvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author vic Zhou
 * @time 2017-12-17 20:52
 * @des 基础类
 */

public abstract class ToBaseFragment extends Fragment {

    public View rootView;//
    public Context mContext;// 上下文
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = initView();
            initData();
            initEvent();
        }
        return rootView;
    }

    /**
     * 初始的view
     *
     * @return
     */
    protected abstract View initView();

    /**
     * 初始数据
     */
    protected void initData() {

    }

    /**
     * 初始事件
     */
    protected void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
