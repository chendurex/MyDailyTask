package com.jvm.instrument.test;

/**
 * @author chen
 * @description
 * @pachage com.jvm.instrument.test
 * @date 2016/05/22 22:06
 */
public class MonitorObj {
    private String name;
    public  String  show(){
        return "hello world!";
    }
    public void setName(String name) {
        this.name = name;
    }
}
