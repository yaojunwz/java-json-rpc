package com.yaojun.java_json_rpc.client;

import com.google.gson.*;
import com.yaojun.java_json_rpc.json_rpc.JsonRpcMethod;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.regex.*;


/**
 * @Author: yaojun
 * @Date: 2018/12/18 14:40
 */
public class JsonRpcAop implements InvocationHandler {
    private Object obj;
    private ProxyConf config;
    private JsonParser jsonParser = new JsonParser();
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Gson gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static long id = 0;

    private static synchronized long getId() {
        return JsonRpcAop.id++;
    }

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

    private void asynHttpRequest(String json, JsonRpcListener listener) throws java.io.IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder().url(this.config.getUrl()).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonObject json = (JsonObject) jsonParser.parse(response.body().string());
                if (json.has("error"))
                    listener.OnCallBack(json.get("id").getAsLong(), json.get("error"), json.get("result"));
                else
                    listener.OnCallBack(json.get("id").getAsLong(), null, json.get("result"));
            }
        });
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
        JsonArray params = new JsonArray();
        for (int i = 0; i < args.length - 1; i++) {
            if ("String".equals(methodTypeParms[i]))
                params.add((String) args[i]);
            else if ("Float".equals(methodTypeParms[i]))
                params.add((float) args[i]);
            else if ("Dict".equals(methodTypeParms[i]))
                params.add((JsonObject) args[i]);
        }
        JsonObject jsonRpcMethod = new JsonObject();
        jsonRpcMethod.addProperty("method", methodName);
        jsonRpcMethod.addProperty("jsonrpc", "2.0");
        jsonRpcMethod.add("params", params);
        jsonRpcMethod.addProperty("id", JsonRpcAop.getId());
        return jsonRpcMethod.toString();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        JsonRpcMethod annotation = method.getDeclaredAnnotationsByType(JsonRpcMethod.class)[0];
        String bodyString = JsonRpcString2JsonString(annotation.Method(), args);
        System.out.println(bodyString);
        if (ProxyConf.Mode.sync == this.config.getMode()) {
            args[args.length - 1] = syncInvoke(bodyString);
            Object ret = method.invoke(obj, args);
            return ret;
        } else {
            asynInvoke(bodyString, args);
        }
        return null;
    }

    public Object syncInvoke(String bodyString) throws Throwable {
        Response response = httpRequest(bodyString);
        String res = response.body().string();
        JsonObject json = (JsonObject) jsonParser.parse(res);
        //System.out.println(res);
        if (json.has("error"))
            throw new JsonRpcException(gs.toJson(json.get("error")));
        return json.get("result");
    }


    public Object asynInvoke(String bodyString, Object[] args) throws Throwable {
        asynHttpRequest(bodyString, (JsonRpcListener) args[args.length - 1]);
        return null;
    }
}
