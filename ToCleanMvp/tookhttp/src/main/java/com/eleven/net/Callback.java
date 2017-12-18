package com.eleven.net;

/**
 * @author vic Zhou
 * @time 2017-12-17 20:25
 * @des 回调
 */

public interface Callback<T> {
    void onResponse(Call<T> var1, Response<T> var2);

    void onFailure(Call<T> var1, Throwable var2);
}
