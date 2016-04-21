package com.design.patterns.singleton_patterns;

import java.io.Serializable;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.singleton_patterns
 * @date 2016/4/11 9:06
 */
public class SingletonObj implements Serializable {
    private String name;
    private static final SingletonObj instance = new SingletonObj();
    private SingletonObj(){}
    public static SingletonObj getInstance() {
        return instance;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void showName(){
        System.out.println("this name is "+name);
    }
}
