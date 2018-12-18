package com.yaojun.java_json_rpc.client;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 14:00
 */
public class ProxyConf {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProxyConf(String url) {
        setUrl(url);
    }
}
