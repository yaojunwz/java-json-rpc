package com.yaojun.java_json_rpc.client.handler;

import com.yaojun.java_json_rpc.client.Handler;
import com.yaojun.java_json_rpc.json_rpc.JsonRpcMethod;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 15:04
 */
public interface TestHandler extends Handler {
    @JsonRpcMethod(Method = "say_hello(String,Float)->dict")
    Object sayHello(String name, float weigth, Object res);
}
