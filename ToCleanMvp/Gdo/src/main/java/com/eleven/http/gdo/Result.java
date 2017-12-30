package com.eleven.http.gdo;

/**
 * @author vic Zhou
 * @time 2017-12-31 1:09
 * @des 结果
 */

public class Result<T> {
    private boolean ok;
    private String message;
    private T result;
    private int code;
    private Object obj;
    private boolean isCache;

    public boolean isOk() {
        return ok;
    }

    public void ok(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ok: ").append(ok).append("\n");
        sb.append("message: ").append(message).append("\n");
        sb.append("obj: ").append(obj).append("\n");
        sb.append("result: ").append(result).append("\n");
        sb.append("code: ").append(code).append("\n");
        sb.append("is_cache: ").append(isCache);

        return sb.toString();
    }
}
