package com.design.patterns.singleton_patterns;


/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.singleton_patterns
 * @date 2016/4/11 9:09
 */
public class Main {
    public static void main(String[] args) {
        SingletonObj instance = SingletonObj.getInstance();
        instance.setName("hello");
        SingletonObj instance2 = SingletonObj.getInstance();
        System.out.println(instance == instance2);
        instance.showName();
    }
}
