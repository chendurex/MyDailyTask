package com.jvm.javassist;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.javassist
 * @date 2016/6/2 9:39
 */
public class CtClassObject {
    public static void main(String[] args) {
    }
    private String name;

    public void show() {
        System.out.println("hi:"+name);
    }
    private void say(String name) {
        System.out.println("say hello" + name);
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
