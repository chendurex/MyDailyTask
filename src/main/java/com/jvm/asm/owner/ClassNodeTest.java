package com.jvm.asm.owner;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author chen
 * @description
 * @pachage com.jvm.asm.owner
 * @date 2016/5/23 9:08
 */
public class ClassNodeTest {
    public static void main(String[] args) {
        ClassNode cn = new ClassNode();
        cn.access = V1_7;
        cn.access = ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE;
        cn.name = "com/jvm/asm/owner/Comparable";
        cn.superName = "java/lang/Object";
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null, Integer.valueOf(-1)));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "MORE", "I", null, Integer.valueOf(0)));
        cn.methods.add(new MethodNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,"compareTo","(LJava/lang/Object;)I",null,null));

        ClassWriter cw = new ClassWriter(0);
        cn.accept(cw);
        cw.toByteArray();
    }
}
