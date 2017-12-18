package com.eleven.tookhttp.retrofit;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author vic Zhou
 * @time 2017-12-17 15:41
 * @des 请求字段的注解
 */

@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface FieldParam {
    boolean encode() default false;
}
