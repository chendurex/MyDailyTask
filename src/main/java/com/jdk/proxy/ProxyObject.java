package com.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chen
 * @description
 * @pachage com.jdk.proxy
 * @date 2016/5/24 19:58
 */
public class ProxyObject<T> implements InvocationHandler {
    private T target;
    public ProxyObject(T target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---------------proxy enter------------------------");
        System.out.println("method is : " + method.getName());
        StringBuilder sb = new StringBuilder();
        if (args != null) {
            sb.append("args is :");
            for (Object obj : args) {
                sb.append(obj.getClass().getName()).append("=").append(obj).append(",");
            }
            System.out.println(sb.subSequence(0,sb.length()-1));
        }
        return method.invoke(target,args);
    }
}
