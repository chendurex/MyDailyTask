package com.jvm.out_of_memory_error;

import org.junit.Test;
import sun.misc.Unsafe;
import sun.net.www.protocol.jar.URLJarFile;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;


/**
 * 内存溢出
 * vmArgs -Xmx10m -Xms10m -Xmn5m -XX:PermSize=5m -XX:MaxPermSize=5m -XX:SurvivorRatio=8 -XX:MaxDirectMemorySize=2m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=c:\temp\ -XX:+PrintGCDetails
 */
public class OutOfMemoryError {

    /**
     * 大对象申请不到空间溢出
     */
    //@MonitorAssign
    public void HeapOutOfMemoryError() {
        byte[] bufOne = new byte[1024 * 1024 * 4];
        byte[] bufTwo = new byte[1024 * 1024 * 4];
    }

    /**
     * 常量池内存溢出
     * 只要把常量池中的对象加入引用无法被jvm回收即可
     * 1.7 将常量池移入到heap区域，所以显示的是heap 溢出
     * 若是1.6则显示 java.lang.OutOfMemoryError: PermGen space
     */
    //@MonitorAssign
    public void constantsPoolOutOfMemeryError() {
        long i = 0;
        String a = "";
        List<String> strings = new ArrayList<>(100000);
        for (;;) {
            a = getClass() + String.valueOf(i);
            a.intern();
            strings.add(a);
            i ++;
        }
    }

    /**
     * 栈溢出 递归调用达到栈的最大深度
     */
    //@MonitorAssign
    public void StackOverflowError() {
        StackOverflowError();
    }
    /**
     * 永久代存储的是类信息，只要加载无限多的类则永久代溢出
     */
    //@MonitorAssign
    public void permGenOutOfMemoryError() throws Exception{
        URLJarFile urlJarFile = new URLJarFile(new File("C:\\Program Files\\Java\\jdk1.7.0_71\\jre\\lib\\rt.jar"));
        Enumeration<JarEntry> jarEntry = urlJarFile.entries();
        for (JarEntry entry = null; jarEntry.hasMoreElements();entry = jarEntry.nextElement()) {
            String clsName = String.valueOf(entry);
            if (clsName != "null" && !clsName.contains("META-INF") && !clsName.contains("$")) {
                String finalName = clsName.replace("/",".");
                finalName = finalName.substring(0,finalName.indexOf(".class"));
                Class<?> cls = getClass().getClassLoader().loadClass(finalName);
                if (cls.getDeclaringClass() != null && cls.getDeclaringClass().getModifiers() == Modifier.PUBLIC) {
                    cls.newInstance();
                }
            }
        }
    }

    /**
     * DirectMemory 默认不指定则跟java堆最大值相等 当然也受限于本身电脑内存
     * 指定MaxDirectMemorySize=2m
     * @throws Exception
     */
    @Test
    public void directBufferOutOfMemoryError() throws Exception{
        ByteBuffer.allocateDirect(2 * 1024 * 1024);
        // 下面使用unsafe类分配内存，则只受限于电脑内存
        /*Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get(new Object());
        unsafe.allocateMemory(100 * 1024 * 1024);*/
    }

}
