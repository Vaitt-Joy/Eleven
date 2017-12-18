package com.eleven.http2.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vic Zhou
 * @time 2017-12-18 23:41
 * @des GET get字段
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface POST {
    String value() default "";
}
