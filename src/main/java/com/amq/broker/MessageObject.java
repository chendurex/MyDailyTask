package com.amq.broker;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MessageObject implements Serializable {
    private static final long serialVersionUID = -8456950348525951359L;
    private String name;
    private int age;

    public MessageObject(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "hi! my name is "+ name + " and my age is :" + age;
    }
}
