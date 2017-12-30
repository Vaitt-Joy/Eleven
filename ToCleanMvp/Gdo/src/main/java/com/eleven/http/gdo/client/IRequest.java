package com.eleven.http.gdo.client;

import com.eleven.http.gdo.Callback;
import com.eleven.http.gdo.Params;

import java.util.LinkedHashMap;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:33
 * @des
 */

public interface IRequest {
    <T> void get(final String url, final LinkedHashMap<String, String> header, final Object tag,
                 final boolean shouldCache, final Callback<T> callback);

    <T> void post(final String url, final LinkedHashMap<String, String> header, final Params params,
                  final Object tag, final boolean shouldCache, final Callback<T> callback);

    <T> void post(final String url, final LinkedHashMap<String, String> header, final String json,
                  final Object tag, final boolean shouldCache, final Callback<T> callback);

    <T> void put(final String url, final LinkedHashMap<String, String> header, final Params params,
                 final Object tag, final boolean shouldCache, final Callback<T> callback);

    <T> void put(final String url, final LinkedHashMap<String, String> header, final String json,
                 final Object tag, final boolean shouldCache, final Callback<T> callback);

    <T> void delete(final String url, final LinkedHashMap<String, String> header,
                    final Object tag, final boolean shouldCache, final Callback<T> callback);
}
