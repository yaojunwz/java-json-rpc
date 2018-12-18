package com.yaojun.java_json_rpc.client;

import com.sun.deploy.net.HttpRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 14:40
 */
public class JsonRpcAop implements InvocationHandler {
    private Object obj;
    private ProxyConf config;

    private OkHttpClient okHttpClient = new OkHttpClient();

    public JsonRpcAop(Object obj, ProxyConf _config) {
        this.obj = obj;
        this.config = _config;
    }

    private Response httpRequest() throws java.io.IOException {
        Request request = new Request.Builder().url(this.config.getUrl()).build();
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method);
        Annotation[] annotations =method.getDeclaredAnnotations();
        System.out.println(annotations.length);

//        Response response = httpRequest();
//        System.out.println(response.body().string());

        Object ret = method.invoke(obj, args);
        System.out.println("后置代理");
        return ret;
    }
}
