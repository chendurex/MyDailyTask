package com.design.patterns.proxy_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.proxy_patterns
 * @date 2016/4/8 9:05
 */
public class Main {
    public static void main(String[] args) {
        IProxy proxy = new ProxyImpl();
        proxy.open();
        proxy.close();
    }
}
