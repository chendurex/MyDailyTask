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

    private ReflectObj ( String name){}

    public void say() {
        System.out.println("no parameter args");
    }
    private void say(String name){
        System.out.println("has parameter args "+ name);
    }
}
