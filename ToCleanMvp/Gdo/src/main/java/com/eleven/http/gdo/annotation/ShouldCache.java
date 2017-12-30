package com.eleven.http.gdo.annotation;

/**
 * @author vic Zhou
 * @time 2017-12-31 1:07
 * @des 缓存
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShouldCache {
}
