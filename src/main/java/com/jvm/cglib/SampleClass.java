package com.jvm.cglib;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.cglib
 * @date 2016/5/25 9:13
 */
public class SampleClass {
    private String name;
    private Integer age;


    public String test(String input) {
        return "hi:" + name + " ,Hello world!" + input;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }
}
