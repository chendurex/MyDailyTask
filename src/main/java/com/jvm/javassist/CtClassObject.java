package com.jvm.javassist;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.javassist
 * @date 2016/6/2 9:39
 */
public class CtClassObject {
    private String name;

    public void show() {
        System.out.println("hi:"+name);
    }
    public String show(String name) {
        return "hi!"+name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
