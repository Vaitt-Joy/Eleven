package com.eleven.http.gdo.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author vic Zhou
 * @time 2017-12-31 1:17
 * @des Helper
 */

public class Helper {
    public static <T> Class<T> getType(Class<?> clazz) {
        Type type = generateType(clazz);
        if (type != null) {
            if (type instanceof ParameterizedType) {
                type = ((ParameterizedType) type).getRawType();
            }
            return (Class<T>) type;
        }
        return null;
    }

    public static Type generateType(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] actualTyps = paramType.getActualTypeArguments();
            if (actualTyps != null && actualTyps.length > 0) {
                return actualTyps[0];
            }
        }
        return null;
    }

    public static <T> Class<T> getDeepType(Class<?> clazz) {
        Type type = generateType(clazz);
        if (type != null && type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<T>) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    public static String getMd5(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
}
