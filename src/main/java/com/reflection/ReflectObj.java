package com.reflection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chen
 * @description TODO
 * @pachage com.reflection
 * @date 2016/4/1 14:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReflectObj <T>{
    private String name;
    private int age;
    private Integer ages;
    public String gender;
    private T he;
    private static String value = "wodiu";

    public ReflectObj(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    private ReflectObj (String name) {
        this.name = name;
    }

    public String say() {
        System.out.println("no parameter args");
        return "say";
    }
    private String say(String name){
        System.out.println("has parameter args "+ name);
        return name;
    }

    private void vm() {

    }

    private static String boot() {
        return "enter";
    }
}
