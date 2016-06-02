package com.jvm.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.javassist
 * @date 2016/6/2 9:37
 */
public class CreateClass {
    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.getCtClass("com.jvm.javassist.CtClassObject");
        CtMethod ctMethod = ctClass.getDeclaredMethod("show");
        ctMethod.insertBefore("System.out.println(\"insert before:\");");
        CtClassObject ctClassObject = (CtClassObject)ctClass.toClass().newInstance();
        ctClassObject.setName("woca");
        ctClassObject.show();
    }
}
