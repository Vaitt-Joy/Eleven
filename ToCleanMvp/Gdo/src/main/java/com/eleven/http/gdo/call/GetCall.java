package com.eleven.http.gdo.call;

import com.eleven.http.gdo.Callback;
import com.eleven.http.gdo.Params;
import com.eleven.http.gdo.client.IClient;

/**
 * @author vic Zhou
 * @time 2017-12-31 3:13
 * @des
 */

public class GetCall<T> extends Call<T> {

    public GetCall(IClient client, String url,
                   Params params, Object tag,
                   boolean cache) {
        super(client, url, params, tag, cache);
    }

    @Override
    public void exec(final Callback<T> callback) {
        String query = mParams.encode();

        String url = mUrl;
        if (query != null) {
            if (url.contains("?")) { url = url + "&" + query;}
            else { url = url + "?" + query;}
        }

        mClient.get(url, mHeaders, mTag, shouldCache, callback);
    }
}
