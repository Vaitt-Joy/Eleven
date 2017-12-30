package com.eleven.http.gdo.client;

import com.eleven.http.gdo.cache.ICacheProvider;
import com.eleven.http.gdo.factory.ParserFactory;
import com.eleven.http.gdo.interceptor.IResultInterceptor;

import java.util.LinkedHashMap;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:31
 * @des IClient 接口
 */

public interface IClient extends IRequest {
    void cancel(final Object tag);
    void parserFactory(ParserFactory factory);
    LinkedHashMap<String, String> headers();
    void resultInterceptor(IResultInterceptor interceptor);
    void cacheProvider(ICacheProvider provider);
    void timeout(long ms);
}
