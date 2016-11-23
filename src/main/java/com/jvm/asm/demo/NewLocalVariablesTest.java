package com.jvm.asm.demo;

import com.util.ClassUtil;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.objectweb.asm.util.CheckMethodAdapter;

import java.io.IOException;

/**
 * @author chen
 * @date 2016/11/15 19:37
 */
public class NewLocalVariablesTest extends ClassLoader {
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.contains(NewLocalVariablesTest.class.getSimpleName())) {
            ClassReader cr = null;
            try {
                cr = new ClassReader(getClass().getResourceAsStream(ClassUtil.getClassFile(name)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new LocalVariableWriter(Opcodes.ASM5, cw);
            cr.accept(cv, 0);
            byte [] b = cw.toByteArray();
            return defineClass(name, b, 0, b.length);
        } else {
            return super.loadClass(name, resolve);
        }
    }

    public static void main(String[] args) throws Exception {
        NewLocalVariablesTest variablesTest = new NewLocalVariablesTest();
        Class<?> clazz = variablesTest.loadClass(variablesTest.getClass().getName(), true);
        clazz.getMethod("localVariable", new Class[] {}).invoke(clazz.newInstance());
    }

    public void localVariable() throws Exception {
        System.out.println("enter method");
    }

    static class LocalVariableWriter extends ClassVisitor {
        public LocalVariableWriter(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.contains("localVariable") && desc.equals("()V")) {
                CheckMethodAdapter checker = new CheckMethodAdapter(mv);
                System.out.println("monitor method suc");
                return new LocalVariablesSorter(Opcodes.ASM5, access, desc, checker) {
                    int localVariable;
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        localVariable = newLocal(Type.getType(Integer.class));
                        //visitVarInsn(Opcodes.ISTORE, localVariable);
                        visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        //visitInsn(Opcodes.DUP);
                        visitLdcInsn(Integer.valueOf(1111));
                        visitVarInsn(Opcodes.ISTORE, localVariable);
                        visitVarInsn(Opcodes.ILOAD, localVariable);
                        visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Integer;)V", false);
                    }

                    @Override
                    public void visitInsn(int opcode) {
                        if (opcode == Opcodes.IRETURN) {
                            super.visitInsn(Opcodes.POP);
                            super.visitVarInsn(Opcodes.ILOAD, localVariable);
                        }
                        super.visitInsn(opcode);
                    }
                };
            }
            return mv;
        }
    }
}
