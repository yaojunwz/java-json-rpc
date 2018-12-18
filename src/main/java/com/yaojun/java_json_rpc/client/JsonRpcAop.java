package com.yaojun.java_json_rpc.client;

import com.yaojun.java_json_rpc.json_rpc.JsonRpcMethod;
import okhttp3.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.*;


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

    private Response httpRequest(String json) throws java.io.IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder().url(this.config.getUrl()).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }


    private String JsonRpcString2JsonString(String jsonRpcString, Object[] args) throws Exception {
        String pattern = "(.*)\\((.*)\\)(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(jsonRpcString);
        String methodName, methodReturnValue;
        String[] methodTypeParms;
        if (m.find()) {
            methodName = m.group(1);
            methodTypeParms = m.group(2).split(",");
            methodReturnValue = m.group(3);
        } else {
            throw new Exception("method format is valid");
        }
        if (!"".equals(config.getNamespace()))
            methodName = config.getNamespace() + "." + methodName;
        String methodParms = "";
        for (int i = 0; i < args.length - 1; i++) {
            if ("String".equals(methodTypeParms[i]))
                methodParms = methodParms+String.format("\"%s\",", args[i].toString());
            else
            methodParms = methodParms + String.format("%s,", args[i].toString());
        }
        methodParms = methodParms.substring(0, methodParms.length() - 1);
        return String.format("{\"jsonrpc\": \"2.0\", \"method\": \"%s\", \"params\": [%s], \"id\": 7}", methodName, methodParms);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //System.out.println(args.length);
        JsonRpcMethod annotation = method.getDeclaredAnnotationsByType(JsonRpcMethod.class)[0];
        String bodyString = JsonRpcString2JsonString(annotation.Method(), args);
        //System.out.println(bodyString);
        Response response = httpRequest(bodyString);
        String res = response.body().string();
        //System.out.println(res);
        args[args.length-1]=res;
        Object ret = method.invoke(obj, args);
        return ret;
    }
}
