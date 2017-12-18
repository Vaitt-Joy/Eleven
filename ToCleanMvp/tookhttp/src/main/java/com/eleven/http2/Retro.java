package com.eleven.http2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author vic Zhou
 * @time 2017-12-19 0:53
 * @des 网络请求
 */

public class Retro {
    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> server) {
        return (T) Proxy.newProxyInstance(server.getClassLoader(), new Class<?>[]{server},
                new InvocationHandler() {
                    private final Platform platform = Platform.get();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getDeclaringClass() == Object.class){
                            return method.invoke(this,args);
                        }
                        if (platform.isDefaultMethod(method)){
                            return platform.invokeDefaultMethod(method,server,proxy,args);
                        }

                        ServiceMethod.Builder builder = new ServiceMethod.Builder(Retro.this, method, args);
                        ServiceMethod serviceMethod = builder.build();
                        return serviceMethod.call();
                    }
                });
    }

    public static final class Builder {

        public Builder(){

        }

        public Retro build(){
            return Retro();
        }
    }
}
