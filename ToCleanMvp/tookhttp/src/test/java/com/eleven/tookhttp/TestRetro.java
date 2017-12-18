package com.eleven.tookhttp;

import com.eleven.http2.Retro;

import org.junit.Test;

/**
 * @author vic Zhou
 * @time 2017-12-19 1:05
 * @des 请求测试类
 */

public class TestRetro {
    @Test
    public void testAnnotation_Url(){
        SimpleRequest simpleRequest = new Retro().
                Builder().
                build().
                create(SimpleRequest.class);
        System.out.print(simpleRequest.getBaidu("http://www.bxvip588.com/index.php/phoneApi/request?ac=getSysInfo"));

    }
}
