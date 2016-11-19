package com.jdk.proxy.rpc;

/**
 * @author chen
 * @description
 * @pachage com.jdk.proxy.rpc
 * @date 2016/11/18 19:02
 */
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        return "Hello " + name;
    }

}
