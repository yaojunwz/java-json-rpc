package com.yaojun.java_json_rpc.client;

import java.lang.reflect.Proxy;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 14:03
 */

public class ClientProxy<T> {
    private ProxyConf config;

    public ClientProxy(ProxyConf _config) {
        this.config = _config;
    }

    public T createHandler(T proxyHandler) {
        JsonRpcAop rpcAop = new JsonRpcAop(proxyHandler,this.config);
        T handler = (T) Proxy.newProxyInstance(proxyHandler.getClass().getClassLoader(), new Class[]{proxyHandler.getClass().getInterfaces()[0]}, rpcAop);
        return handler;
    }
}
