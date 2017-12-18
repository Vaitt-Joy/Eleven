package com.eleven.net.response;

import org.json.JSONObject;

/**
 * @author vic Zhou
 * @time 2017-12-17 19:42
 * @des json 数据接口回调
 */

public abstract class JsonResponseCallback implements IResponseCallback {
    public abstract void onSuccess(int statusCode, JSONObject response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
