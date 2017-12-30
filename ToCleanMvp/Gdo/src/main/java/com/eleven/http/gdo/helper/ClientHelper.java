package com.eleven.http.gdo.helper;

import com.eleven.http.gdo.Callback;
import com.eleven.http.gdo.RawResult;
import com.eleven.http.gdo.Result;
import com.eleven.http.gdo.cache.ICacheProvider;
import com.eleven.http.gdo.factory.ParserFactory;
import com.eleven.http.gdo.interceptor.IResultInterceptor;

import java.util.List;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:08
 * @des 连接管理
 */

public class ClientHelper {
    /**
     * 解析工厂
     */
    private ParserFactory mParserFactory;
    /**
     * 缓存
     */
    private ICacheProvider mCacheProvider;
    /**
     * 拦截器
     */
    private IResultInterceptor mResultInterceptor;

    public ClientHelper parserFactory(ParserFactory parserFactory) {
        mParserFactory = parserFactory;
        return this;
    }

    public ClientHelper cacheProvider(ICacheProvider cacheProvider) {
        mCacheProvider = cacheProvider;
        return this;
    }

    public ClientHelper resultInterceptor(IResultInterceptor resultInterceptor) {
        mResultInterceptor = resultInterceptor;
        return this;
    }

    /**
     * convert the http response to Result
     *
     * @param callback
     * @param netResult
     * @param <T>
     * @return
     */
    public <T> Result<T> parseResponse(Callback<T> callback, RawResult netResult) {
        final Class<?> callbackClazz = callback.getClass();
        final Class<T> dataClazz = Helper.getType(callbackClazz);
        final boolean resultTypeIsArray = resultTypeIsArray(dataClazz);

        Result<T> result;
        if (resultTypeIsArray) {
            Class<T> tClass = Helper.getDeepType(callbackClazz);
            result = mParserFactory.getListParser().parse(tClass, netResult);
        } else {
            result = mParserFactory.getParser().parse(dataClazz, netResult);
        }
        return result;
    }

    /**
     * 获取缓存的key
     *
     * @param url
     * @param params
     * @return
     */
    public String getCacheKey(String url, String params) {
        return mCacheProvider == null ? "" : mCacheProvider.getKey(url, params);
    }

    /**
     * ues cache
     *
     * @param shouldCache
     * @param cacheKey
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Result<T> useCache(boolean shouldCache, String cacheKey, Callback<T> callBack) {
        final Class<?> callbackKlass = callBack.getClass();
        final Class<T> dataClazz = Helper.getType(callbackKlass);
        final boolean resultTypeIsArray = resultTypeIsArray(dataClazz);
        if (shouldCache && mCacheProvider != null) {
            Result<T> result;
            if (resultTypeIsArray) {
                Class<T> tClass = Helper.getDeepType(callbackKlass);
                result = mCacheProvider.get(cacheKey, dataClazz, true);
            } else {
                result = mCacheProvider.get(cacheKey, dataClazz, false);
            }
            if (result != null && result.isOk()) {
                return result;
            }
        }
        return null;
    }

    /**
     * cache your response if should cache
     *
     * @param shouldCache
     * @param cacheKey
     * @param result
     * @param netResult
     * @param <T>
     * @return
     */
    public <T> boolean cache(boolean shouldCache, String cacheKey, Result<T> result,
                             RawResult netResult) {
        if (shouldCache && mCacheProvider != null && result.isOk()) {
            mCacheProvider.put(cacheKey, netResult, result);
            return true;
        }
        return false;
    }

    /**
     * testing should intercept callback
     *
     * @param result
     * @param <T>
     * @return
     */
    public <T> boolean shouldInterceptResult(final Result<T> result) {
        return mResultInterceptor != null && mResultInterceptor.intercept(result);
    }

    private <T> boolean resultTypeIsArray(Class<T> dataClazz) {
        return List.class.isAssignableFrom(dataClazz);
    }
}
