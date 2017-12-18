package com.eleven.newNet;

import java.io.IOException;

import okhttp3.Callback;

/**
 * @author vic Zhou
 * @time 2017-12-19 0:10
 * @des  网络请求接口
 */
public interface Call extends Cloneable {

    /**
     * 同步请求
     *
     * @return
     * @throws IOException
     */
    String execute() throws IOException;

    /**
     * 异步请求
     *
     * @param callback
     */
    void enqueue(Callback callback);

}

