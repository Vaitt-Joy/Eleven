package com.eleven.tookhttp;

import com.eleven.http2.http.GET;
import com.eleven.http2.http.Url;

/**
 * @author vic Zhou
 * @time 2017-12-19 1:06
 * @des 测试实例
 */

public interface SimpleRequest {
    @GET
    String getBaidu(@Url String url);
}
