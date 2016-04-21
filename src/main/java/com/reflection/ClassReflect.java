package com.reflection;


import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * @author chen
 * @description TODO
 * @pachage com.reflection
 * @date 2016/4/1 14:20
 */
public class ClassReflect {
    @Test
    public void getClassModifiers() {
        String classPath = "com.reflection.ReflectObj";
        try {
            Class<?> class_= Class.forName(classPath);
            //获取修饰符
            System.out.println(Modifier.isPublic(class_.getModifiers()));
            //实现接口
            System.out.println(class_.getInterfaces());
            //所在的包信息
            System.out.println(class_.getPackage());
            //构造方法
            Constructor<?> [] constructor = class_.getConstructors();

            for (Constructor cons : constructor) {
                // 打印构造函数信息
                System.out.println(cons.toGenericString());
                // 打印参数信息
                System.out.println(cons.getParameterTypes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
