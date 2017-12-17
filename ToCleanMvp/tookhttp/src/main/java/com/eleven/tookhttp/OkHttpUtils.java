package com.eleven.tookhttp;

import okhttp3.OkHttpClient;

/**
 * @author vic Zhou
 * @time 2017-12-17 14:52
 * @des okhttp 网络请求工具类
 * 封装基本的网络请求
 * 扩展其对数据库的支持
 * 对多文件上传、多线程文件下载的支持
 * 对Json数据解析等功能的支持
 */

public class OkHttpUtils {

    private OkHttpUtils IST;// 单例实例
    private OkHttpClient client;

    private OkHttpUtils() {
        if (IST == null) {
            IST = new OkHttpUtils();
            client = new OkHttpClient();
        }
    }

    public static void get() {

    }

    public static void post() {

    }

    public static void downloadFile() {

    }
}
