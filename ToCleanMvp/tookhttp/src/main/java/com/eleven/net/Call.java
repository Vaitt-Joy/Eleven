package com.eleven.net;

import android.app.VoiceInteractor;

import java.io.IOException;

import okhttp3.Request;

/**
 * @author vic Zhou
 * @time 2017-12-17 20:17
 * @des 请求封装
 */

public interface Call<T> extends Cloneable {
    Response<T> execute() throws IOException;

    void enqueue(Callback<T> callback);

    boolean isExecute();

    void cancel();

    boolean isCancel();

    Call<T> clone();

    Request request();
}
