package com.jdk.mbean;

/**
 * @author cheny.huang
 * @date 2019-04-30 09:47.
 */
public interface HelloMBean {
    // operations

    void sayHello();
    int add(int x, int y);

    // attributes

    // a read-only attribute called Name of type String
    String getName();

    // a read-write attribute called CacheSize of type int
    int getCacheSize();
    void setCacheSize(int size);
}
