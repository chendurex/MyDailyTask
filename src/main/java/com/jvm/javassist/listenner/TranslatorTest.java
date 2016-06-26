package com.jvm.javassist.listenner;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.javassist.listenner
 * @date 2016/6/4 10:30
 */
public class TranslatorTest {
    public static void main(String[] args) throws Throwable {
        Translator t = new MyTranslator();
        ClassPool pool = ClassPool.getDefault();
        Loader cl = new Loader();
        cl.addTranslator(pool, t);
        cl.run("com.jvm.javassist.CtClassObject", args);
    }
}
