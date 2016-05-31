package com.jvm.cglib.mixin;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib.mixin
 * @date 2016/5/31 9:27
 */
public class Class1 implements Interface1 {
    @Override
    public String first() {
        return "first";
    }
}
