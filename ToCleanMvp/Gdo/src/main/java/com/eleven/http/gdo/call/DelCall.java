package com.eleven.http.gdo.call;

import com.eleven.http.gdo.Callback;
import com.eleven.http.gdo.Params;
import com.eleven.http.gdo.client.IClient;

/**
 * @author vic Zhou
 * @time 2017-12-31 3:15
 * @des ${TODO}
 */

public class DelCall<T> extends Call<T> {

    public DelCall(IClient client, String url,
                   Params params, Object tag,
                   boolean cache) {
        super(client, url, params, tag, cache);
    }

    @Override
    public void exec(Callback<T> callback) {
        mClient.delete(mUrl, mHeaders, mTag, shouldCache, callback);
    }
}