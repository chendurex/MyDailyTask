package com.jdk.singleton;

/**
 * Created by LENOVO on 2016/3/15.
 */
//静态工厂创建
public class StaticFactorySingleton {
    private static final StaticFactorySingleton singleton = new StaticFactorySingleton();
    public static StaticFactorySingleton getInstance() {
        return singleton;
    }
}
