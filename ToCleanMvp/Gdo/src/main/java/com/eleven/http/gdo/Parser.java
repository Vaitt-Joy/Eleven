package com.eleven.http.gdo;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:11
 * @des 解析器
 */

public abstract class Parser {
    public String mKey;

    public Parser() {

    }

    public Parser(String key) {
        mKey = key;
    }

    /**
     * @param clazz     the class of data struct
     * @param netResult
     * @param <T>
     * @return
     */
    public abstract <T> Result<T> parse(Class<T> clazz, RawResult netResult);
}
