package com.yaojun.java_json_rpc;

import com.yaojun.java_json_rpc.client.ClientProxy;
import com.yaojun.java_json_rpc.client.Handler;
import com.yaojun.java_json_rpc.client.ProxyConf;
import com.yaojun.java_json_rpc.client.handler.TestHandler;
import com.yaojun.java_json_rpc.client.handler.TestHandlerImp;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 13:52
 */
public class ClientTest {

    public static void main(String[] args) {
        try {
            // setup  ClientProxy
            ClientProxy<TestHandler> clientProxy = new ClientProxy(new ProxyConf("http://apiserver.smartgreenai.com/json/"));

            // get handler
            TestHandler handler = clientProxy.createHandler(new TestHandlerImp());

            // run jsonRpcMethod
            handler.sayHello("yaojun", 83, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
