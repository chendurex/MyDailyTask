package com.design.patterns.proxy_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.proxy_patterns
 * @date 2016/4/8 9:18
 */
public class JDKProxyImpl implements JDKIProxy {
    @Override
    public void jdkOpen(String name) {
        System.out.println("jdk impl "+name);
    }

    @Override
    public void jdkClose(String name) {
        System.out.println("jdk impl "+name);
    }
}
