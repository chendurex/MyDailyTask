package com.jvm.instrument.test;

/**
 * Created by Administrator on 2016/5/29.
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
