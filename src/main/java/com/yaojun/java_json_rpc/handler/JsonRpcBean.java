package com.yaojun.java_json_rpc.handler;

/**
 * @Author: yaojun
 * @Date: 2018/12/25 19:28
 */
public class JsonRpcBean {
    private String nickname;
    private Integer sex;
    private String mobile;

    @Override
    public String toString() {
        return "JsonRpcBean{" +
                "nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
