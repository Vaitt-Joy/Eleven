package com.eleven.newNet;

import com.eleven.newNet.http.ServiceMethod;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author vic Zhou
 * @time 2017-12-19 0:13
 * @des ${TODO}
 */

public class OkHttpCall implements Call {

    private ServiceMethod mServiceMethod;

    private static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }

    public OkHttpCall(ServiceMethod serviceMethod) {
        this.mServiceMethod = serviceMethod;
    }

    @Override
    public String execute() throws IOException {
        if (mServiceMethod.getMethodName().equals("GET")) {
            Request request = new Request.Builder()
                    .url(mServiceMethod.getBaseUrl())
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        return null;
    }

    @Override
    public void enqueue(final Callback callback) {
        if (mServiceMethod.getMethodName().equals("GET")) {
            Request request = new Request.Builder()
                    .url(mServiceMethod.getBaseUrl())
                    .build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    callback.onFailure(call, e);
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    callback.onResponse(call, response);
                }
            });
        }
    }
}
