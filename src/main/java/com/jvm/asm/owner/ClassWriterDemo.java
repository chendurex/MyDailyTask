package com.jvm.asm.owner;

import org.objectweb.asm.ClassWriter;

import static org.objectweb.asm.Opcodes.*;
/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.asm.owner
 * @date 2016/5/13 19:57
 */
public class ClassWriterDemo extends ClassLoader{
    @Override
    public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
        if (name.contains("ClassWriterDemo")) {
            byte [] b = writeClass();
            return defineClass("com.jvm.asm.owner.Comparable",b,0,b.length);
        } else {
            return super.loadClass(name,resolve);
        }
    }

    public static void main(String[] args) throws Exception{
        ClassWriterDemo classWriterDemo = new ClassWriterDemo();
        System.out.println(classWriterDemo.loadClass("com.jvm.asm.owner.ClassWriterDemo"));
    }
    public byte[] writeClass() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_7, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "com/jvm/asm/owner/Comparable", null, "java/lang/Object",
                new String[] {"java/util/List"});
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, Integer.valueOf(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        return cw.toByteArray();
    }
}
