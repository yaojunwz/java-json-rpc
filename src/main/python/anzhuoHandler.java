package com.anzhuo.java_json_rpc.handler;

import com.google.gson.JsonObject;
import com.yaojun.java_json_rpc.client.Handler;
import com.yaojun.java_json_rpc.client.JsonRpcException;
import com.yaojun.java_json_rpc.json_rpc.JsonRpcMethod;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 15:04
 */

public interface anzhuoHandler extends Handler {
    
	@JsonRpcMethod(Method = "sendRegisterCode(String )->String") 
	default String sendRegisterCode(String mobile, Object res) throws JsonRpcException {
	        return (String) res;
	}

	@JsonRpcMethod(Method = "appUserRegisterMobile(String ,String )->String") 
	default String appUserRegisterMobile(String mobile,String code, Object res) throws JsonRpcException {
	        return (String) res;
	}

	@JsonRpcMethod(Method = "updateUser(String ,Dict )->Dict") 
	default Dict updateUser(String token,Dict info, Object res) throws JsonRpcException {
	        return (Dict) res;
	}

}
