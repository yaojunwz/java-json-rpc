package com.yaojun.java_json_rpc.handler;

import java.util.Arrays;

/**
 * @Author: yaojun
 * @Date: 2018/12/29 12:33
 */
public class SosListBean {
    Integer total;
    SosBean[] data;

    @Override
    public String toString() {
        return "SosListBean{" +
                "total=" + total +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}