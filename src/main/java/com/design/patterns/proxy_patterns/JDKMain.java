package com.design.patterns.proxy_patterns;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.proxy_patterns
 * @date 2016/4/8 9:12
 */
public class JDKMain {
    public static void main(String[] args) {
        Class<?> [] classes = new Class[]{JDKIProxy.class};
        JDKIProxy proxy = (JDKIProxy) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), classes,
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(new JDKProxyImpl(),args);
                    }
                });
        proxy.jdkOpen("opening");
        proxy.jdkClose("closing");
    }
}
