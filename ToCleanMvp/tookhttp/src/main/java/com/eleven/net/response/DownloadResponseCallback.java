package com.eleven.net.response;

import java.io.File;

/**
 * @author vic Zhou
 * @time 2017-12-17 19:31
 * @des 下载回调
 */

public abstract class DownloadResponseCallback {
    public abstract void onFinish(File download_file);
    public abstract void onProgress(long currentBytes, long totalBytes);
    public abstract void onFailure(String error_msg);
}
