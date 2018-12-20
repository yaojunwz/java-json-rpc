package com.yaojun.java_json_rpc;

import com.google.gson.JsonObject;
import com.yaojun.java_json_rpc.client.ClientProxy;
import com.yaojun.java_json_rpc.client.ProxyConf;
import com.yaojun.java_json_rpc.client.JsonRpcException;
import com.yaojun.java_json_rpc.client.handler.TestHandler;
import com.yaojun.java_json_rpc.client.handler.TestHandlerImp;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 13:52
 */

public class ClientTest {
    public static void main(String[] args) {
        try {
            // 设置 ClientProxy 代理配置
            ClientProxy<TestHandler> clientProxy = new ClientProxy(new ProxyConf("http://host/json"));

            // 获得 handler 代理处理类
            TestHandler handler = clientProxy.createHandler(new TestHandlerImp());

            // 运行 jsonRpcMethod 服务器方法
            //handler.sendCode("17625407444", null);
            //Object a = handler.getThirdList("d2b23a791a6316eab40e58ed5092cded", null);
            JsonObject a = handler.updateUser("d2b23a791a6316eab40e58ed5092cded", new JsonObject(), null);
            System.out.println(a);
        } catch (JsonRpcException e) {
            System.out.println(e);
        }
    }
}