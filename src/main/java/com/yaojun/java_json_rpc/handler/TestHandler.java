package com.yaojun.java_json_rpc.handler;

import com.google.gson.JsonObject;
import com.yaojun.java_json_rpc.client.Handler;
import com.yaojun.java_json_rpc.client.JsonRpcException;
import com.yaojun.java_json_rpc.json_rpc.JsonRpcMethod;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 15:04
 */
public interface TestHandler extends Handler {
    @JsonRpcMethod(Method = "sendRegisterCode(String)->String")
    default String sendRegisterCode(String mobile, Object res) throws JsonRpcException {
        return (String) res;
    }

    @JsonRpcMethod(Method = "getThirdList(String)->Dict")
    default Object getThirdList(String token, Object res) throws JsonRpcException {
        return res;
    }

    @JsonRpcMethod(Method = "updateUser(String,Dict)->Dict")
    default JsonObject updateUser(String token, JsonObject info, Object res) throws JsonRpcException {
        return (JsonObject) res;
    }
}
