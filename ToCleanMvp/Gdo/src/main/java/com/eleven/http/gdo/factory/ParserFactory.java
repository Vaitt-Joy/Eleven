package com.eleven.http.gdo.factory;

import com.eleven.http.gdo.Parser;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:12
 * @des 解析工厂
 */

public interface ParserFactory {
    Parser getParser();

    Parser getListParser();
}
