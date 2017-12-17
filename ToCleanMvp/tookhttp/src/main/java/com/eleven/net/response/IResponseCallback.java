package com.eleven.net.response;

/**
 * @author vic Zhou
 * @time 2017-12-17 19:27
 * @des 回调接口
 */

public interface IResponseCallback {
    void onFailure(int statusCode, String error_msg);

    void onProgress(long currentBytes, long totalBytes);
}
