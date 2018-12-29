package com.yaojun.java_json_rpc.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yaojun.java_json_rpc.client.JsonRpcException;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 14:08
 */

public class TestHandlerImp implements TestHandler {
    private Gson gs = new Gson();

    
@Override
public Object sendRegisterCode(String mobile, Object res) throws JsonRpcException { return (Object)res; }


@Override
public String appUserRegisterMobile(String mobile,String code, Object res) throws JsonRpcException { return (String)res; }


	@Override
	public UserBean updateUser(String token,UserBean info, Object res) throws JsonRpcException {return gs.fromJson((JsonObject) res, UserBean.class); }


	@Override
	public SosListBean addSos(String token,String name,String phone, Object res) throws JsonRpcException {return gs.fromJson((JsonObject) res, SosListBean.class); }


	@Override
	public SosListBean delSos(String token,String phone, Object res) throws JsonRpcException {return gs.fromJson((JsonObject) res, SosListBean.class); }


}
