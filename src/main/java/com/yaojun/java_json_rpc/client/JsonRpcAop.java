package com.yaojun.java_json_rpc.client;

import com.google.gson.*;
import com.yaojun.java_json_rpc.json_rpc.JsonRpcMethod;
import okhttp3.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
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

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }

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

    private void asynHttpRequest(String methodName, String json, JsonRpcListener listener) throws java.io.IOException {
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
                    listener.OnCallBack(methodName, json.get("error"), json.get("result"));
                else
                    listener.OnCallBack(methodName, null, json.get("result"));
            }
        });
    }


    private JsonObject JsonRpcString2JsonString(String jsonRpcString, Object[] args) throws Exception {
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
            else if ("Int".equals(methodTypeParms[i]))
                params.add((int) args[i]);
            else if ("Dict".equals(methodTypeParms[i]))
                params.add((JsonObject) args[i]);
            else if ("JsonObject".equals(methodTypeParms[i]))
                params.add((JsonObject) args[i]);
            else
                params.add(gs.toJsonTree(args[i]));
        }
        JsonObject jsonRpcMethod = new JsonObject();
        jsonRpcMethod.addProperty("method", methodName);
        jsonRpcMethod.addProperty("jsonrpc", "2.0");
        jsonRpcMethod.add("params", params);
        jsonRpcMethod.addProperty("id", JsonRpcAop.getId());
        jsonRpcMethod.addProperty("return", methodReturnValue);
        return jsonRpcMethod;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        JsonRpcMethod annotation = method.getDeclaredAnnotationsByType(JsonRpcMethod.class)[0];
        JsonObject body = JsonRpcString2JsonString(annotation.Method(), args);
        System.out.println(body);
        if (ProxyConf.Mode.sync == this.config.getMode()) {
            args[args.length - 1] = syncInvoke(body);
            Object ret = method.invoke(obj, args);
            return ret;
        } else {
            asynInvoke(method.getName(), body, args);
        }
        return null;
    }

    public Object syncInvoke(JsonObject body) throws Throwable {
        Response response = httpRequest(body.toString());
        String res = response.body().string();
        JsonObject json = (JsonObject) jsonParser.parse(res);
        if (json.has("error")) {
            throw new JsonRpcException(gs.toJson(json.get("error")), json.get("error").getAsJsonObject());
        }
        if (body.get("return").getAsString().endsWith("Class")) {
            return json.get("result").getAsJsonObject();
        }
        return json.get("result");
    }


    public Object asynInvoke(String methodName, JsonObject body, Object[] args) throws Throwable {
        asynHttpRequest(methodName, body.toString(), (JsonRpcListener) args[args.length - 1]);
        return null;
    }
}
