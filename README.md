# java-json-rpc
json-rpc 2.0协议实现 (基于http请求)

快速开始

1 安装 第三方依赖 gson 和 okhttp

2 将后端 jsonRpc 的方法 写入 TestHandler 

3 测试主方法(同步)
public static void main(String[] args) {
    // 配置代理 服务器地址 方法域名 回调模式(同步)
    ClientProxy<TestHandler> clientProxy = new ClientProxy(
            new ProxyConf(url, "", ProxyConf.Mode.sync));
    // 配置代理处理类
    TestHandler handler = clientProxy.createHandler(new TestHandlerImp());
    // 运行服务器端方法
    System.out.println("回调模式(同步)");
    JsonObject result = handler.updateUser("1725a3df5b354191143e83b914fa0c51", new JsonObject(), null);
    System.out.println(gs.toJson(result));
}


4 测试主方法(异步)
public static void main(String[] args) {
    ClientTest clientTest = new ClientTest();
    // 配置代理 服务器地址 方法域名 回调模式(异步)
    clientProxy = new ClientProxy(
            new ProxyConf(url, "", ProxyConf.Mode.asyn));
    handler = clientProxy.createHandler(new TestHandlerImp());
    System.out.println("回调模式(异步)");
    handler.updateUser("1725a3df5b354191143e83b914fa0c51", info, clientTest);
}
