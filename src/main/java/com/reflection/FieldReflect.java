package com.reflection;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * @author chen
 * @description TODO
 * @pachage com.reflection
 * @date 2016/4/1 15:02
 */
public class FieldReflect {
    @Test
    public void getMethod() throws Exception{
        Class<?> class_ = Thread.currentThread().getContextClassLoader().loadClass("com.reflection.ReflectObj");
        Field[] fields = class_.getDeclaredFields();
        Object obj = class_.newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> type = field.getType();
            if (type.equals(String.class)) {
                field.set(obj,"hello");
            } else if (type.equals(Integer.class)) {
                field.set(obj,12);
            } else if (type.equals(int.class)) {
                field.set(obj,1);
            } else if(type.equals(ParameterizedType.class)) {
                field.set(obj,"word");
            }
            System.out.println(field.getType()+"===="+field.getGenericType());
        }
        System.out.println(obj);
    }
}
