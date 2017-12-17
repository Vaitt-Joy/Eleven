package com.eleven.tomvp.mvp;

import com.eleven.tomvp.http.BaseCacheManager;
import com.eleven.tomvp.http.BaseServiceManager;

/**
 * @author vic Zhou
 * @time 2017-12-17 21:17
 * @des ${TODO}
 */

public class BaseModel<S extends BaseServiceManager, C extends BaseCacheManager> implements IModel {

    protected S mServiceManager;//服务管理类,用于网络请求
    protected C mCacheManager;//缓存管理类,用于管理本地或者内存缓存

    public BaseModel(S serviceManager, C cacheManager) {
        this.mServiceManager = serviceManager;
        this.mCacheManager = cacheManager;
    }

    @Override
    public void onDestroy() {
        if (mServiceManager != null) {
            mServiceManager = null;
        }
        if (mCacheManager != null) {
            mCacheManager = null;
        }
    }
}
