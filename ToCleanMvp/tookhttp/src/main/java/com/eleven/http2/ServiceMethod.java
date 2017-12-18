package com.eleven.http2;

import com.eleven.http2.http.GET;
import com.eleven.http2.http.POST;
import com.eleven.http2.http.Url;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;

/**
 * @author vic Zhou
 * @time 2017-12-19 0:55
 * @des 封装类
 */

public class ServiceMethod {
    private final String mBaseUrl;

    private final String mHttpMethod;

    ServiceMethod(Builder builder) {
        mBaseUrl = builder.baseUrl;
        mHttpMethod = builder.httpMethod;
    }

    public Object call() {
        return new HttpCall(this);
    }

    public String getUrl() {
        return mBaseUrl;
    }

    public String getHttpMethod() {
        return mHttpMethod;
    }

    // 封装请求头
    public void applyHeader(HttpURLConnection connection) {

    }

    public static final class Builder {

        private String baseUrl;

        private Object args[];

        private String httpMethod;

        private Class<?>[] methodParamsTypes;

        private final Annotation[] methodAnnotations;
        private final Annotation[][] methodParamsAnnotations;

        public Builder(Retro retro, Method method, Object[] args) {
            this.args = args;
            this.methodAnnotations = method.getAnnotations();
            this.methodParamsAnnotations = method.getParameterAnnotations();
            this.methodParamsTypes = method.getParameterTypes();
        }

        public ServiceMethod build() {
            for (int i = 0; i < methodAnnotations.length; i++) {
                parseMethodAnnotation(methodAnnotations[i]);
            }
            for (int i = 0; i < methodParamsAnnotations.length; i++) {
                parseMethodParamsAnnotation(methodParamsAnnotations[i], args[i], methodParamsTypes[i]);
            }

            return new ServiceMethod(this);
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            }
        }

        private void parseMethodParamsAnnotation(Annotation[] annotations, Object value, Class<?> type) {
            if (annotations.length == 1) {
                Annotation annotation = annotations[0];
                if (annotation instanceof Url) {
                    baseUrl = String.valueOf(value);
                }
            }
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            this.httpMethod = httpMethod;
        }
    }
}
