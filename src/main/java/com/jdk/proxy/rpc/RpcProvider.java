package com.jdk.proxy.rpc;

/**
 * @author chen
 * @description
 * @pachage com.jdk.proxy.rpc
 * @date 2016/11/18 19:02
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }

}
