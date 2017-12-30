package com.eleven.http.gdo.cache;

import com.eleven.http.gdo.RawResult;
import com.eleven.http.gdo.Result;
import com.eleven.http.gdo.helper.Helper;
import com.eleven.http.gdo.helper.SerializeHelper;

import java.io.File;

/**
 * @author vic Zhou
 * @time 2017-12-31 1:31
 * @des 默认的缓存
 */

public class DefaultCacheProvider implements ICacheProvider {
    private static final String SUFFIX = ".Gc";

    private String mCachePath;
    private long maxCacheSize;

    public DefaultCacheProvider(String cacherPath, long maxCacheSize) {
        mCachePath = cacherPath.endsWith("/") ? cacherPath : cacherPath + "/";
        maxCacheSize = maxCacheSize;

        File cache = new File(mCachePath);
        if (!cache.exists()) {
            for (int i = 0; i < 3; i++) {
                if (cache.mkdirs()) {
                    break;
                }
            }
        }
    }

    /**
     * 获取结果
     *
     * @param key    key
     * @param klass  type of data struct, see also {@link com.eleven.http.gdo.helper.Helper}
     * @param isList is result a List
     * @param <T>
     * @return
     */
    @Override
    public <T> Result<T> get(String key, Class<T> klass, boolean isList) {
        T cacheResult = SerializeHelper.unSerialize(mCachePath, key + SUFFIX);
        if (cacheResult == null) {
            return null;
        }
        Result<T> result = new Result<>();
        result.ok(true);
        result.setMessage("");
        result.setResult(cacheResult);
        result.setObj(200);
        result.setCache(true);
        return result;
    }

    /**
     * 保存缓存
     *
     * @param key       see {@link #getKey(String, String)}
     * @param netResult the origin result
     * @param result    parsed result
     * @param <T>
     */
    @Override
    public <T> void put(String key, RawResult netResult, Result<T> result) {
        checkCacheSize();
        SerializeHelper.serialize(mCachePath, key + SUFFIX, result.getResult());
    }

    /**
     * 获取缓存信息
     *
     * @param url    the request url
     * @param params the request params, maybe key1=value1&key2=value2 while your request is POST, maybe null while your request is GET, maybe json string while your request is POST Json
     * @return
     */
    @Override
    public String getKey(String url, String params) {
        String cacheName = url + (params == null ? "" : params);
        cacheName = Helper.getMd5(cacheName);
        return null;
    }

    /**
     * 检查缓存
     */
    private void checkCacheSize() {
        File dir = new File(mCachePath);
        File[] files = dir.listFiles();
        long size = 0L;

        for (File file : files) {
            size += file.length();
        }

        if (size >= maxCacheSize) {
            clearCache(files);
        }
    }

    /**
     * 清除缓存
     *
     * @param files
     */
    private void clearCache(File[] files) {
        TAG:
        for (File file : files) {
            for (int i = 0; i < 3; i++) {
                if (file.delete()) {
                    continue TAG;
                }
            }
        }
    }
}
