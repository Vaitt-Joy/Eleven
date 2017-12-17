package com.eleven.net;

import android.support.annotation.Nullable;

/**
 * @author vic Zhou
 * @time 2017-12-17 20:21
 * @des 工具类
 */

public class Utils {
    static <T> T checkNotNull(@Nullable T object, String message) {
        if(object == null) {
            throw new NullPointerException(message);
        } else {
            return object;
        }
    }
}
