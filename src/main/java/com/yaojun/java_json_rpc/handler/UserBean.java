package com.yaojun.java_json_rpc.handler;

/**
 * @Author: yaojun
 * @Date: 2018/12/29 11:19
 */
public class UserBean {
    public String id;
    public String nickname;
    public String mobile;
    public Integer sex;

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex=" + sex +
                '}';
    }
}
