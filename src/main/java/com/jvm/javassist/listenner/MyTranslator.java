package com.jvm.javassist.listenner;

import javassist.*;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.javassist.listenner
 * @date 2016/6/4 10:28
 */
public class MyTranslator implements Translator {
    @Override
    public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
        System.out.println("===============start enter===========================");
    }

    @Override
    public void onLoad(ClassPool classPool, String s) throws NotFoundException, CannotCompileException {
        System.out.println("===============class:"+s+" onLoad===========================");
        CtClass cc = classPool.get(s);
        cc.setModifiers(Modifier.PUBLIC);
    }
}
