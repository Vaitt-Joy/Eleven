package com.eleven.virtualhost;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.didi.virtualapk.PluginManager;

import java.io.File;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by vic on 17-12-15.
 */

public class HostApp extends Application {
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 可以加载插件
        PluginManager pluginManager = PluginManager.getInstance(this);
        //此处是当查看插件apk是否存在,如果存在就去加载(比如修改线上的bug,把插件apk下载到sdcard的根目录下取名为webview1.1.1.apk)
        File apk = new File(getExternalStorageDirectory(), "webview1.1.1.apk");
        if (apk.exists()) {
            try {
                Log.i(TAG, "准备加载...");
                pluginManager.loadPlugin(apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
