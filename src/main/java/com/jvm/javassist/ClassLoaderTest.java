package com.jvm.javassist;

import javassist.ClassPool;
import javassist.Loader;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.javassist
 * @date 2016/6/4 10:19
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        Loader cl = new Loader(pool);
        Class c = cl.loadClass("com.jvm.javassist.CtClassObject");
        Object rect = c.newInstance();
        System.out.println(rect);
        CtClassObject ctClassObject = (CtClassObject)rect;
    }
}
