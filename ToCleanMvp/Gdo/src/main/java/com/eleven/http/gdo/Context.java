package com.eleven.http.gdo;

import com.eleven.http.gdo.call.Call;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:25
 * @des Context.class
 */

public class Context {
    private Call<?> call;
    private Result<?> result;
    private RawResult rawResult;

    public Context() {}

    public Context(Call<?> call) {
        this.call = call;
    }

    public Call<?> getCall() {
        return call;
    }

    public void setCall(Call<?> call) {
        this.call = call;
    }

    public Result<?> getResult() {
        return result;
    }

    public void setResult(Result<?> result) {
        this.result = result;
    }

    public RawResult getRawResult() {
        return rawResult;
    }

    public void setRawResult(RawResult rawResult) {
        this.rawResult = rawResult;
    }
}
