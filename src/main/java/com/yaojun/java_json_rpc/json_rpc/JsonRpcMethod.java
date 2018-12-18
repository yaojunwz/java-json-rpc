package com.yaojun.java_json_rpc.json_rpc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 14:19
 */
@Target(ElementType.METHOD)
public @interface JsonRpcMethod {
    String Method();
}
