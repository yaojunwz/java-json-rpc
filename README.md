# java-json-rpc
json-rpc 2.0协议实现 (基于http请求)


// 设置 ClientProxy 代理配置
ClientProxy<TestHandler> clientProxy = new ClientProxy(new ProxyConf("http://host/json"));

// 获得 handler 代理处理类
TestHandler handler = clientProxy.createHandler(new TestHandlerImp());

// 运行 jsonRpcMethod 服务器方法
//handler.sendCode("17625407444", null);
//Object a = handler.getThirdList("d2b23a791a6316eab40e58ed5092cded", null);
JsonObject a = handler.updateUser("d2b23a791a6316eab40e58ed5092cded", new JsonObject(), null);
System.out.println(a);