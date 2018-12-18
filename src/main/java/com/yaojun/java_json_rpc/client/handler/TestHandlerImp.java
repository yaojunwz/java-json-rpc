package com.yaojun.java_json_rpc.client.handler;

import com.yaojun.java_json_rpc.client.Handler;
import com.yaojun.java_json_rpc.json_rpc.JsonRpcMethod;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 14:08
 */

public class TestHandlerImp implements TestHandler {
    public Object sayHello(String name, float weigth, Object res) {
        return res;
    }
}
