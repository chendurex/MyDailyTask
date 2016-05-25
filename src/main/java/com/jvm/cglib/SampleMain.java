package com.jvm.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/25 9:15
 */
public class SampleMain {
    @Test
    public void test() throws Exception{
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "hello enhancer test!";
            }
        });
        SampleClass sampleClass = (SampleClass) enhancer.create();
        Assert.assertEquals("Hello cglib!", sampleClass.test("hello cglib!"));
    }
}
