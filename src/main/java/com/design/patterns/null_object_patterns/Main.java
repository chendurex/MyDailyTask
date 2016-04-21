package com.design.patterns.null_object_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.null_object_patterns
 * @date 2016/4/13 9:32
 */
public class Main {
    public static void main(String[] args) {
        String [] names = {"bob","cheny","joke","killy","june"};
        for (String name : names) {
            System.out.println(ConsumerFactory.getConsumer(name).getName());
        }
    }
}
