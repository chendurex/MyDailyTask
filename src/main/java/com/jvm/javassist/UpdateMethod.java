package com.jvm.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;


/**
 * Created by Administrator on 2016/6/5.
 */
public class UpdateMethod {
    public static void main(String[] args) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.jvm.javassist.CtClassObject");
        CtMethod ctMethod = ctClass.getMethod("show","(Ljava/lang/String;)Ljava/lang/String;");
        ctMethod.insertBefore("{System.out.println(\"insert before\");}");
        ctMethod.insertBefore("{System.out.println(\"parameter is :\"+$1);}");
        CtClassObject ctClassObject = (CtClassObject) ctClass.toClass().newInstance();
        ctClassObject.show("111");
    }
}
