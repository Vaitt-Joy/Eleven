package com.eleven.http.gdo.call;

import com.eleven.http.gdo.Callback;
import com.eleven.http.gdo.Params;
import com.eleven.http.gdo.client.IClient;

/**
 * @author vic Zhou
 * @time 2017-12-31 3:13
 * @des
 */

public class PostCall<T> extends Call<T> {

    public PostCall(IClient client, String url,
                    Params params, Object tag,
                    boolean cache) {
        super(client, url, params, tag, cache);
    }

    @Override
    public void exec(final Callback<T> callback) {
        mClient.post(mUrl, mHeaders, mParams, mTag, shouldCache, callback);
    }
}