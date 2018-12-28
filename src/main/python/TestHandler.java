package com.yaojun.java_json_rpc.handler;

import com.google.gson.JsonObject;
import com.yaojun.java_json_rpc.client.Handler;
import com.yaojun.java_json_rpc.client.JsonRpcException;
import com.yaojun.java_json_rpc.json_rpc.JsonRpcMethod;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 15:04
 */

public interface TestHandler extends Handler {
    
	@JsonRpcMethod(Method = "sendRegisterCode(String)->Object") 
	default Object sendRegisterCode(String mobile, Object res) throws JsonRpcException {
	        return (Object) res;
	}

	@JsonRpcMethod(Method = "appUserRegisterMobile(String,String)->String") 
	default String appUserRegisterMobile(String mobile,String code, Object res) throws JsonRpcException {
	        return (String) res;
	}

	@JsonRpcMethod(Method = "updateUser(String,JsonObject)->JsonObject") 
	default JsonObject updateUser(String token,JsonObject info, Object res) throws JsonRpcException {
	        return (JsonObject) res;
	}

	@JsonRpcMethod(Method = "addSos(String,String,String)->JsonObject") 
	default JsonObject addSos(String token,String name,String phone, Object res) throws JsonRpcException {
	        return (JsonObject) res;
	}

	@JsonRpcMethod(Method = "getSos(String)->JsonObject") 
	default JsonObject getSos(String token, Object res) throws JsonRpcException {
	        return (JsonObject) res;
	}

}
