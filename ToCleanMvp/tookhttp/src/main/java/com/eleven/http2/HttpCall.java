package com.eleven.http2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author vic Zhou
 * @time 2017-12-19 1:34
 * @des http 执行类
 */

public class HttpCall {

    private ServiceMethod mServiceMethod;

    interface Callback {
        void onResponse(Response response);
    }

    public HttpCall(ServiceMethod serviceMethod) {
        mServiceMethod = serviceMethod;
    }

    public Response execute() {
        HttpURLConnection connection;
        Response response;
        try {
            String urlStr = mServiceMethod.getUrl();
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);

            //set http method
            connection.setRequestMethod(mServiceMethod.getHttpMethod());

            //set header
            mServiceMethod.applyHeader(connection);

            response = new Response(connection);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void submit(final Callback callback){
        Response response = execute();
        if(callback != null){
            callback.onResponse(response);
        }
    }
}
