package com.jvm.cglib.mixin;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib.mixin
 * @date 2016/5/31 9:27
 */
public class Class2 implements Interface2 {
    @Override
    public String second() {
        return "second";
    }
}
