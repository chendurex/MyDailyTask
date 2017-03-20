package com.jvm.instrument.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

/**
 * @author chen
 * @description
 * @pachage com.jvm.instrument.test
 * @date 2017/3/20 14:23
 */
public class ClassEnhancer {

    public static void main(String[] args) throws Exception {
        for(;;) {
            create();
            Thread.sleep(5000);
        }
    }

    public static ClassEnhancer create() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ClassEnhancer.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Hello cglib!";
            }
        });
        return (ClassEnhancer) enhancer.create();
    }
}
