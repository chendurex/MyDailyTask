package com.jdk.proxy;

import java.lang.reflect.Proxy;

/**
 * @author chen
 * @description TODO
 * @pachage com.jdk.proxy
 * @date 2016/5/24 20:04
 */
public class ProxyMain {
    public static void main(String[] args) {
        ComparableTest comparableTest = new ComparableTest("test");
        Comparable comparable = (Comparable) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(),
                new Class[]{Comparable.class},new ProxyObject(comparableTest));
        System.out.println(comparable.compareTo("test2"));
    }
}
