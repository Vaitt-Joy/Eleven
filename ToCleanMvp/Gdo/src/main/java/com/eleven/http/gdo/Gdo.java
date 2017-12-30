package com.eleven.http.gdo;

import com.eleven.http.gdo.annotation.JSON;
import com.eleven.http.gdo.annotation.Param;
import com.eleven.http.gdo.annotation.Path;
import com.eleven.http.gdo.annotation.ShouldCache;
import com.eleven.http.gdo.cache.ICacheProvider;
import com.eleven.http.gdo.call.Call;
import com.eleven.http.gdo.chan.GlobalChanNode;
import com.eleven.http.gdo.client.IClient;
import com.eleven.http.gdo.factory.CallFactory;
import com.eleven.http.gdo.factory.ParserFactory;
import com.eleven.http.gdo.interceptor.IResultInterceptor;
import com.eleven.http.gdo.utils.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Gdo, A retrofit like network framework <br />
 * <p>
 * Usage: <br />
 * 1. write your client and parser, config gdo
 * Gdo gdo = new Gdo.Builder()
 * .client(new OkClient())
 * .baseUrl("http://192.168.201.39")
 * .debug(true)
 * .parserFactory(new FastJsonParserFactory())
 * .timeout(10000)
 * .build();
 * <p>
 * 2. create an interface
 * public interface UserBiz {
 *
 * @POST("/users/list") Call<User> list(@Param("name") String userName);
 * }
 * <p>
 * 3. request the network and callback
 * UserBiz biz = gdo.create(UserBiz.class, getClass().getName());
 * Call<User> call = biz.list("qibin");
 * call.enqueue(new Callback<User>() {
 * @Override public void onResponse(Result<User> result) {
 * if (result.isOK()) {
 * Toast.makeText(MainActivity.this, result.getResult().getName(), Toast.LENGTH_SHORT).show();
 * }else {
 * Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
 * }
 * }
 * });
 */

public class Gdo {
    private Builder mBuilder;
    private CallFactory mCallFactory;

    private Gdo(Builder builder) {
        mBuilder = builder;
        mCallFactory = new CallFactory();
    }

    public <T> T create(Class<T> tClass, Object tag) {
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),
                new Class<?>[]{tClass}, new Handler(tag));
    }

    class Handler implements InvocationHandler {
        private Object mTag;

        public Handler(Object tag) {
            mTag = tag;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<? extends Annotation> key = null;
            String path = null;

            HashMap<Class<? extends Annotation>, Class<? extends Call>> mapping = mCallFactory.get();
            Class<? extends Annotation> item;
            Annotation anno;
            for (Iterator<Class<? extends Annotation>> iterator = mapping.keySet().iterator();
                 iterator.hasNext(); ) {
                item = iterator.next();
                if (method.isAnnotationPresent(item)) {
                    key = item;
                    anno = method.getAnnotation(item);
                    path = (String) anno.getClass().getDeclaredMethod("value").invoke(anno);
                    break;
                }
            }

            if (key == null) {
                throw new UnsupportedOperationException("cannot find annotations");
            }

            Class<? extends Call> callKlass = mCallFactory.get(key);
            if (callKlass == null) {
                throw new UnsupportedOperationException("cannot find calls");
            }

            boolean shouldCache = method.isAnnotationPresent(ShouldCache.class);

            Pair<String, Params> pair = new Pair<>(justUrl(path), new Params());
            params(pair, method, args);

            Constructor<? extends Call> constructor = callKlass.getConstructor(IClient.class,
                    String.class, Params.class, Object.class, boolean.class);

            Call<?> call = constructor.newInstance(mBuilder.client, pair.first, pair.second,
                    mTag, shouldCache);

            call.setGlobalChanNode(mBuilder.beforeGlobalChanNode, mBuilder.afterGlobalChanNode);

            return call;
        }

        private String justUrl(String path) {
            String url = mBuilder.baseUrl == null ? "" : mBuilder.baseUrl;
            path = path == null ? "" : path;
            if (isFullUrl(path)) {
                url = path;
            } else {
                url += path;
            }
            return url;
        }

        private boolean isFullUrl(String url) {
            if (url == null || url.length() == 0) {
                return false;
            }
            if (url.toLowerCase().startsWith("http://")) {
                return true;
            }
            if (url.toLowerCase().startsWith("https://")) {
                return true;
            }
            return false;
        }

        private void params(Pair<String, Params> pair, Method method, Object[] args) {
            if (args == null || args.length == 0) {
                return;
            }

            // method.getParameterAnnotations.length always equals args.length
            Annotation[][] paramsAnno = method.getParameterAnnotations();
//            if (method.isAnnotationPresent(JSON.class)) {
//                params.add(Params.DEFAULT_JSON_KEY, args[0]);
//                return params;
//            }

            int length = paramsAnno.length;
            for (int i = 0; i < length; i++) {
                if (paramsAnno[i].length == 0) {
                    // there is no annotation on this param,
                    // so, maybe it is a json value when the method is JSON annotation presented
                    if (method.isAnnotationPresent(JSON.class)) {
                        pair.second.add(Params.DEFAULT_JSON_KEY, args[i]);
                    }
                } else {
                    if (paramsAnno[i][0] instanceof Param) {
                        pair.second.add(((Param) paramsAnno[i][0]).value(), args[i]);
                    } else if (paramsAnno[i][0] instanceof Path) {
                        pair.first = pair.first.replaceAll("\\{:" + ((Path) paramsAnno[i][0]).value() + "\\}",
                                args[i].toString());
                    }
                }
            }
        }
    }

    public static class Builder {

        private IClient client;
        private String baseUrl;

        private GlobalChanNode beforeGlobalChanNode;
        private GlobalChanNode afterGlobalChanNode;

        public Builder() {

        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder client(IClient client) {
            this.client = client;
            return this;
        }

        public Builder globalChanNode(GlobalChanNode before, GlobalChanNode after) {
            this.beforeGlobalChanNode = before;
            this.afterGlobalChanNode = after;
            return this;
        }

        public Builder parserFactory(ParserFactory factory) {
            if (this.client == null) {
                throw new UnsupportedOperationException("invoke client method first");
            }
            this.client.parserFactory(factory);
            return this;
        }

        public Builder cacheProvider(ICacheProvider cacheProvider) {
            if (this.client == null) {
                throw new UnsupportedOperationException("invoke client method first");
            }
            this.client.cacheProvider(cacheProvider);
            return this;
        }

        public Builder timeout(long ms) {
            if (this.client == null) {
                throw new UnsupportedOperationException("invoke client method first");
            }
            this.client.timeout(ms);
            return this;
        }

        public Builder resultInterceptor(IResultInterceptor interceptor) {
            if (this.client == null) {
                throw new UnsupportedOperationException("invoke client method first");
            }
            this.client.resultInterceptor(interceptor);
            return this;
        }

        public Gdo build() {
            return new Gdo(this);
        }
    }
}
