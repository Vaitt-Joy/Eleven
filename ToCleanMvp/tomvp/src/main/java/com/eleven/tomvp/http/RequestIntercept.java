package com.eleven.tomvp.http;

import android.support.annotation.NonNull;

import com.eleven.tomvp.utils.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author vic Zhou
 * @time 2017-12-17 23:32
 * @des ${TODO}
 */

public class RequestIntercept implements Interceptor {

    private GlobeHttpHandler mHandler;

    public RequestIntercept(GlobeHttpHandler handler) {
        this.mHandler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        if (mHandler != null)//在请求服务器之前可以拿到request,做一些操作比如给request添加header,如果不做操作则返回参数中的request
            request = mHandler.onHttpRequestBefore(chain, request);


        Buffer requestbuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestbuffer);
        }

        long t1 = System.nanoTime();
        Response originalResponse = chain.proceed(request);
        long t2 = System.nanoTime();

        //读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        //获取content的压缩类型
        String encoding = originalResponse
                .headers()
                .get("Content-Encoding");

        Buffer clone = buffer.clone();
        String bodyString;

        //解析response content
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content使用gzip压缩
            bodyString = ZipHelper.decompressForGzip(clone.readByteArray());//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content使用zlib压缩
            bodyString = ZipHelper.decompressToStringForZlib(clone.readByteArray());//解压
        } else {//content没有被压缩
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            bodyString = clone.readString(charset);
        }

        if (mHandler != null)//这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);

        return originalResponse;
    }

    @NonNull
    public static String parseParams(RequestBody body, Buffer requestbuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestbuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

}
