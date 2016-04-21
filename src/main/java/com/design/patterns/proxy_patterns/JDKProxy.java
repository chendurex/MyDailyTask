package com.design.patterns.proxy_patterns;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.proxy_patterns
 * @date 2016/4/8 9:07
 */
public class JDKProxy implements InvocationHandler {
    private Object obj;
    public JDKProxy(Object obj) {
        this.obj = obj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(obj,args);
    }
}
