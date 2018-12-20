package com.yaojun.java_json_rpc.client;

/**
 * @Author: yaojun
 * @Date: 2018/12/20 13:50
 */
public interface JsonRpcListener {
    void OnCallBack(long id, Object err, Object result);
}
