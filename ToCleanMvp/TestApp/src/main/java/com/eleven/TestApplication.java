package com.eleven;

import android.app.Application;

import com.eleven.lib.commenlib.utils.Utils;

/**
 * <pre>
 *     author: vic
 *     time  : 17-12-18
 *     desc  : TODO
 * </pre>
 */

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
