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
	Object sendRegisterCode(String mobile, Object res) throws JsonRpcException;


	@JsonRpcMethod(Method = "appUserRegisterMobile(String,String)->String")
	String appUserRegisterMobile(String mobile,String code, Object res) throws JsonRpcException;


	@JsonRpcMethod(Method = "updateUser(String,UserBean)->UserBean")
	UserBean updateUser(String token,UserBean info, Object res) throws JsonRpcException;


	@JsonRpcMethod(Method = "addSos(String,String,String)->SosListBean")
	SosListBean addSos(String token,String name,String phone, Object res) throws JsonRpcException;


	@JsonRpcMethod(Method = "delSos(String,String)->SosListBean")
	SosListBean delSos(String token,String phone, Object res) throws JsonRpcException;


}
