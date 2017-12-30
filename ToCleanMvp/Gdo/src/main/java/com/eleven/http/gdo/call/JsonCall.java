package com.eleven.http.gdo.call;

import com.eleven.http.gdo.Callback;
import com.eleven.http.gdo.Params;
import com.eleven.http.gdo.client.IClient;

/**
 * @author vic Zhou
 * @time 2017-12-31 3:11
 * @des
 */

public class JsonCall<T> extends Call<T> {

    public JsonCall(IClient client, String url, Params params, Object tag, boolean cache) {
        super(client, url, params, tag, cache);
    }

    @Override
    public void exec(Callback callback) {
        String json = mParams.getParams(Params.DEFAULT_JSON_KEY);
        if (json == null) {
            throw new UnsupportedOperationException("cannot find json");
        }
        mClient.post(mUrl, mHeaders, mParams.getParams(Params.DEFAULT_JSON_KEY), mTag, shouldCache, callback);
    }
}
