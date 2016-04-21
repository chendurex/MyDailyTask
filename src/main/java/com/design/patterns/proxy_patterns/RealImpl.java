package com.design.patterns.proxy_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.proxy_patterns
 * @date 2016/4/8 9:02
 */
public class RealImpl implements IProxy {
    @Override
    public void open() {
        System.out.println("real open");
    }

    @Override
    public void close() {
        System.out.println("real close");
    }
}
