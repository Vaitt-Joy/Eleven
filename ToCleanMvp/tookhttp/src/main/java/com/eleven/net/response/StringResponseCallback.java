package com.eleven.net.response;

/**
 * @author vic Zhou
 * @time 2017-12-17 19:43
 * @des string 字符串结果回调
 */

public abstract class StringResponseCallback implements IResponseCallback {
    public abstract void onSuccess(int statusCode, String response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
