package com.jvm.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/25 9:39
 */
public class InvocationHandlerProxy {
    @Test
    public void test() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
                    return "invocation method handle";
                } else {
                    throw new Exception("nothing to do");
                }

            }
        });
        SampleClass sampleClass = (SampleClass) enhancer.create();
        Assert.assertEquals("invocation method handle",sampleClass.test(null));
        Assert.assertEquals("test",sampleClass.hashCode());
    }
}
