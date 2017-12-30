package com.eleven.http.gdo;

import com.eleven.http.gdo.chan.ChanNode;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:25
 * @des 回调接口
 */

public abstract class Callback<T> {
    private Context mContext;
    private ChanNode mAfterChanNode;

    public final void attach(Context ctx, ChanNode afterChanNode) {
        mContext = ctx;
        mAfterChanNode = afterChanNode;
    }

    public final void afterResponse(Result<T> result, RawResult rawResult) {
        mContext.setRawResult(rawResult);
        mContext.setResult(result);

        if (mAfterChanNode != null) {
            mAfterChanNode.exec(mContext);
        }
    }

    public abstract void onResponse(Result<T> result);
}
