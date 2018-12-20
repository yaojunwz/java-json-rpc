package com.yaojun.java_json_rpc.client;

/**
 * @Author: yaojun
 * @Date: 2018/12/18 14:00
 */
public class ProxyConf {

    public enum Mode {
        sync,
        asyn,
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    private String namespace;

    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public ProxyConf.Mode getMode() {
        return mode;
    }

    public void setMode(ProxyConf.Mode mode) {
        this.mode = mode;
    }

    private Mode mode;


    public ProxyConf(String url) {
        setNamespace("");
        setUrl(url);
        setMode(Mode.sync);
    }

    public ProxyConf(String url, String namespace,Mode mode) {
        setNamespace(namespace);
        setUrl(url);
        setMode(mode);
    }
}
