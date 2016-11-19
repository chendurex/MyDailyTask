package com.jdk.proxy;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author chen
 * @description
 * @pachage com.jdk.proxy
 * @date 2016/5/24 20:04
 */
public class ProxyMain extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        return super.loadClass(name);
    }

    public static void main(String[] args) throws Exception{
        ComparableTest comparableTest = new ComparableTest("test");
        Comparable comparable1 = (Comparable) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(),
                new Class[]{Comparable.class},new ProxyObject(comparableTest));
        System.out.println(comparable1.compareTo("test2"));


        Comparable comparable2 = (Comparable) Proxy.getProxyClass(ProxyMain.class.getClassLoader(), Comparable.class)
                .getConstructor(new Class[] { InvocationHandler.class })
                .newInstance(new Object[] {new ProxyObject(comparableTest)});
        System.out.println(comparable2.compareTo("test2"));

        Comparable comparable3 = new $Proxy0(new ProxyObject(comparableTest));
        System.out.println(comparable3.compareTo("test2"));
      //  byte[] b = ProxyGenerator.generateProxyClass("com.sun.proxy.$Proxy0", new Class[] {Comparable.class});
       // FileOutputStream fos = new FileOutputStream(new File("d:\\$Proxy0.class"));
        //fos.write(b);
       // fos.close();
    }
}
