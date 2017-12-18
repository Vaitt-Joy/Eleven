package com.eleven.tookhttp.retrofit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author vic Zhou
 * @time 2017-12-17 15:48
 * @des baseUrl
 */

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface HttpBaseUrl {
}
