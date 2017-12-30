package com.eleven.http.gdo.factory;

import com.eleven.http.gdo.annotation.DEL;
import com.eleven.http.gdo.annotation.GET;
import com.eleven.http.gdo.annotation.JSON;
import com.eleven.http.gdo.annotation.POST;
import com.eleven.http.gdo.annotation.PUT;
import com.eleven.http.gdo.call.Call;
import com.eleven.http.gdo.call.DelCall;
import com.eleven.http.gdo.call.GetCall;
import com.eleven.http.gdo.call.JsonCall;
import com.eleven.http.gdo.call.PostCall;
import com.eleven.http.gdo.call.PutCall;

import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:12
 * @des
 */

public class CallFactory {
    private HashMap<Class<? extends Annotation>, Class<? extends Call>> mMapping = new HashMap<>();

    public CallFactory() {
        autoRegist();
    }

    private void autoRegist() {
        register(JSON.class, JsonCall.class);
        register(GET.class, GetCall.class);
        register(POST.class, PostCall.class);
        register(PUT.class, PutCall.class);
        register(DEL.class, DelCall.class);
    }

    public void register(Class<? extends Annotation> key, Class<? extends Call> value) {
        mMapping.put(key, value);
    }

    public Class<? extends Call> get(Class<? extends Annotation> key) {
        return mMapping.get(key);
    }

    public HashMap<Class<? extends Annotation>, Class<? extends Call>> get() {
        return mMapping;
    }
}
