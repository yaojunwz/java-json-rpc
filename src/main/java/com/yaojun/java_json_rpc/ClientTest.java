package com.yaojun.java_json_rpc;

import com.google.gson.*;
import com.yaojun.java_json_rpc.client.ClientProxy;
import com.yaojun.java_json_rpc.client.JsonRpcListener;
import com.yaojun.java_json_rpc.client.ProxyConf;
import com.yaojun.java_json_rpc.client.JsonRpcException;
import com.yaojun.java_json_rpc.handler.TestHandler;
import com.yaojun.java_json_rpc.handler.TestHandlerImp;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 13:52
 */

public class ClientTest implements JsonRpcListener {
    public static void main(String[] args) {
        Gson gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        ClientTest clientTest = new ClientTest();
        try {
            String url = "your_host";
            JsonObject info = new JsonObject();
            info.addProperty("nickname", "yaojun");

            // 配置代理 服务器地址 方法域名 回调模式(同步)
            ClientProxy<TestHandler> clientProxy = new ClientProxy(
                    new ProxyConf(url, "", ProxyConf.Mode.sync));
            // 配置代理处理类
            TestHandler handler = clientProxy.createHandler(new TestHandlerImp());
            // 运行服务器端方法
            //handler.sendCode("17625407444", null);
            //Object a = handler.getThirdList("1725a3df5b354191143e83b914fa0c51", null);
            System.out.println("回调模式(同步)");
            JsonObject result = handler.updateUser("1725a3df5b354191143e83b914fa0c51", info, clientTest);
            System.out.println(gs.toJson(result));

            // 配置代理 服务器地址 方法域名 回调模式(异步)
            clientProxy = new ClientProxy(
                    new ProxyConf(url, "", ProxyConf.Mode.asyn));
            handler = clientProxy.createHandler(new TestHandlerImp());
            System.out.println("回调模式(异步)");
            handler.updateUser("1725a3df5b354191143e83b914fa0c51", info, clientTest);

        } catch (JsonRpcException e) {
            System.out.println(e);
        }
    }

    @Override
    public void OnCallBack(String methodName, Object err, Object result) {
        System.out.println(methodName);
        System.out.println(err);
        Gson gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        System.out.println(gs.toJson(result));
    }
}