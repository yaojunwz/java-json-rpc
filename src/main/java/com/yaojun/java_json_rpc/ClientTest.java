package com.yaojun.java_json_rpc;

import com.google.gson.*;
import com.yaojun.java_json_rpc.client.ClientProxy;
import com.yaojun.java_json_rpc.client.JsonRpcListener;
import com.yaojun.java_json_rpc.client.ProxyConf;
import com.yaojun.java_json_rpc.client.JsonRpcException;
import com.yaojun.java_json_rpc.handler.*;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 13:52
 */

public class ClientTest {
    public static void main(String[] args) {
        Gson gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        ClientTest clientTest = new ClientTest();
        try {
            String url = "http://apiserver.smartgreenai.com/json";
            UserBean userBean = new UserBean();
            userBean.nickname = "yaojunBean";
            // 配置代理 服务器地址 方法域名 回调模式(同步)
            ClientProxy<TestHandler> clientProxy = new ClientProxy(new ProxyConf(url, "", ProxyConf.Mode.sync));
            // 配置代理处理类
            TestHandler handler = clientProxy.createHandler(new TestHandlerImp());
            // 运行服务器端方法
            //handler.sendCode("17625407444", null);
            //Object a = handler.getThirdList("1725a3df5b354191143e83b914fa0c51", null);
            System.out.println("回调模式(同步)");
//            UserBean result = handler.updateUser("bc24bc2d50a65abb3c87cfcd1edf0afc", userBean, clientTest);
//            System.out.println(result);
            SosListBean result = handler.addSos("bc24bc2d50a65abb3c87cfcd1edf0afc","yaojun2","17625407443", null);
            System.out.println(result);
        } catch (JsonRpcException e) {
            System.out.println("异常");
            System.out.println(e.getCode());
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println("异常");
        }
    }
}