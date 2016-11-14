package com.jvm.asm.owner;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;
/**
 * @author chen
 * @description
 * @pachage com.jvm.asm.owner
 * @date 2016/5/11 9:36
 */
public class ClassPrinter extends ClassVisitor {
    public ClassPrinter() {
        super(ASM4);
    }
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        /*System.out.println(name + " extends " + superName + " {");*/
        System.out.println(name+"visit");
    }
    public void visitSource(String source, String debug) {
        System.out.println(source+"visitSource");
    }
    public void visitOuterClass(String owner, String name, String desc)
    {
        System.out.println(name+"visitOutClass");
    }
    public AnnotationVisitor visitAnnotation(String desc,
                                             boolean visible) {
        System.out.println(desc+"visitAnnotation");
        return null;
    }
    public void visitAttribute(Attribute attr) {
        System.out.println("visitAttribute");
    }
    public void visitInnerClass(String name, String outerName,
                                String innerName, int access) {
        System.out.println(outerName+"---"+innerName+"  visitInnerClass");
    }
    public FieldVisitor visitField(int access, String name, String
            desc,
                                   String signature, Object value) {
        //System.out.println(" " + desc + " " + name);
        System.out.println(name+"visitField");
        return null;
    }
    public MethodVisitor visitMethod(int access, String name,
                                     String desc, String signature, String[] exceptions) {
        //System.out.println(" " + name + desc);
        System.out.println(name+"  "+desc+"  visitMethod");
        return null;
    }
    public void visitEnd() {
        System.out.println("visitEnd");
        //System.out.println("}");
    }

    public static void main(String[] args) throws Exception{
        ClassPrinter cp = new ClassPrinter();
        //ClassReader cr = new ClassReader("java.lang.Runnable");
        ClassReader cr = new ClassReader("com.jvm.asm.owner.VisitOrderDemo");
        cr.accept(cp, 0);
    }
}
