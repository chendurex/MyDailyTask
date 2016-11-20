package com.jvm.asm.demo;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;


/**
 * @author chen
 * @description
 * @pachage com.jvm.asm.owner
 * @date 2016/5/13 20:52
 */
public class ChangeClassVersion  extends ClassLoader{
    public Class defineClass(String name, byte[] b){
        return defineClass(name, b, 0, b.length);
    }
    public static void main(String[] args) throws Exception{
        ChangeClassVersion changeClassVersion = new ChangeClassVersion();
        changeClassVersion.change();

    }

    public void change() throws Exception{
        ClassReader classReader = new ClassReader(getClass().getResourceAsStream("ChangeClassVersion.class"));
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new ChangeVisitor(Opcodes.ASM4,classWriter);
        classReader.accept(classVisitor,0);
        byte []b = classWriter.toByteArray();
        defineClass("com.jvm.asm.demo.ChangeClassVersion", b);
    }

    static class ChangeVisitor extends ClassVisitor{
        public ChangeVisitor(int api ,ClassWriter classWriter) {
            super(api,classWriter);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(Opcodes.V1_8, access, name, signature, superName, interfaces);
        }
    }
}
