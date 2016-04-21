package com.design.patterns.proxy_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.proxy_patterns
 * @date 2016/4/8 9:02
 */
public class ProxyImpl implements IProxy {
    private IProxy proxy = new RealImpl();
    @Override
    public void open() {
        proxy.open();
    }

    @Override
    public void close() {
        proxy.close();
    }
}
