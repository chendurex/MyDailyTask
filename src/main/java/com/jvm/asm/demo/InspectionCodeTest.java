package com.jvm.asm.demo;

import com.util.ClassUtil;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.IOException;

/**
 * @author chen
 * @date 2016/11/13 13:50
 */
public class InspectionCodeTest extends ClassLoader{

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.contains(InspectionCodeTest.class.getSimpleName())) {
           byte[] b;
            try {
                ClassReader cr = new ClassReader(getClass().getResourceAsStream(ClassUtil.getClassFile(name)));
                ClassWriter cw = new ClassWriter(cr, 0);
                ClassVisitor cv = new MonitorExecuteTimeClassVisitor(Opcodes.ASM4, cw);
                cr.accept(cv, 0);
                b = cw.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return defineClass(name, b, 0, b.length);
        } else {
            return super.loadClass(name, resolve);
        }
    }

    public static void main(String[] args) throws Exception{
        Class<?> clazz = new InspectionCodeTest().loadClass(InspectionCodeTest.class.getName());
        clazz.getMethod("run", new Class<?>[]{}).invoke(clazz.newInstance(), null);
    }

    public static void run() {
        System.out.println("running");
    }

    public static void exit() {
        System.out.println("exit");
    }

    static class AdviceAdapterTest extends AdviceAdapter {
        public AdviceAdapterTest(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            System.out.println("inspection enter");
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            try {
                visitMethodInsn(INVOKESTATIC, ClassUtil.dotPathToSlantPath(InspectionCodeTest.class, "/"),
                        "exit", "()V", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void endMethod() {
            super.endMethod();
            System.out.println("end method");
        }
    }

    static class MonitorExecuteTimeClassVisitor extends ClassVisitor {
        private ClassVisitor cv;
        public MonitorExecuteTimeClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
            this.cv = cv;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (access == (Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)
                    && name.equals("run")
                    & desc.equals("()V")) {
                System.out.println("monitor method is : " + name);
               // return new MonitorMethodVisitor(Opcodes.ASM4, mv);
                return new AdviceAdapterTest(Opcodes.ASM4, mv, access, name, desc);
            }
            return mv;
        }

    }

    static class MonitorMethodVisitor extends MethodVisitor {
        private MethodVisitor mv;
        public MonitorMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv);
            this.mv = mv;
        }

        @Override
        public void visitCode() {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("run injection");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitEnd();
            super.visitCode();
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(maxStack + 2, maxLocals);
        }
    }

}
