package com.yaojun.java_json_rpc.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @Author: yaojun
 * @Date: 2018/12/20 11:04
 */
public class JsonRpcException extends Exception {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonRpcException(String message, JsonObject messageObject) {
        super(message);
        setCode(messageObject.get("code").getAsInt());
        setMessage(messageObject.get("message").getAsString());
    }
}
