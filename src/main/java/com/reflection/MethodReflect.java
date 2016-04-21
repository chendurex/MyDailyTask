package com.reflection;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author chen
 * @description TODO
 * @pachage com.reflection
 * @date 2016/4/1 15:29
 */
public class MethodReflect {
    @Test
    public void getMethod() throws Exception{
        Class<?> class_ = getClass().getClassLoader().loadClass("com.reflection.ReflectObj");
        Object obj = class_.newInstance();
        Method methods [] = class_.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.getName().indexOf("say") != -1) {
                if( method.getParameterTypes().length == 0) {
                    method.invoke(obj,null);
                } else if (method.getParameterTypes().length == 1) {
                    method.invoke(obj,"hello");
                }
            }

            //method.invoke(obj,method.getParameterTypes());
        }
    }
}
