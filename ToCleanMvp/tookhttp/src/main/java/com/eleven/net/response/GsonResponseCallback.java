package com.eleven.net.response;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author vic Zhou
 * @time 2017-12-17 19:37
 * @des gson 数据返回的解析回调
 */

public abstract class GsonResponseCallback<T> implements IResponseCallback {
    Type mType;

    public GsonResponseCallback() {
        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    public final Type getType() {
        return mType;
    }

    public abstract void onSuccess(int statusCode, T response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
