package com.eleven.http.gdo.interceptor;

import com.eleven.http.gdo.Result;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:10
 * @des 拦截器接口
 */

public interface IResultInterceptor {
    /**
     * 是否拦截结果
     * @param result
     * @return true callback不会执行
     */
    boolean intercept(Result<?> result);
}
