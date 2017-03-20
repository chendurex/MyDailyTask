package com.jvm.instrument;

/**
 * @author chen
 * @description
 * @pachage com.jvm.instrument
 * @date 2016/05/22 22:06
 */

import com.jvm.instrument.test.MonitorObj;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class ClassTransform implements ClassFileTransformer {
    private Instrumentation inst;

    protected ClassTransform(Instrumentation inst) {
        this.inst = inst;
    }

    /**
     * 此方法只有在Instrumentation显式的调用addTransformer添加一个转换器才会执行
     * 在class初次加载或者再次加载时，会调用此方法
     * 此方法主要是动态的修改字节码，这里我采用javassist实现动态插入代码
     */
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("当前ClassName=" + className + ",ClassLoader=" + loader + ",classBeingRedefined=" + classBeingRedefined
                + ",protectionDomain=" + protectionDomain + ",classfileBuffer.getClass" + classfileBuffer.getClass());
        if (!classBeingRedefined.getName().equals(MonitorObj.class.getName())) {
            return null;
        }
        byte[] byteCode = classfileBuffer;
        try {

            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            CtMethod[] methods = ctClass.getDeclaredMethods();
            for (CtMethod method : methods) {
                method.addLocalVariable("startTime", CtClass.longType);
                method.insertBefore("startTime = System.nanoTime();");
                method.insertAfter("System.out.println(\"Execution Duration "
                        + "(nano sec): \"+ (System.nanoTime() - startTime) );");
            }
            byteCode = ctClass.toBytecode();
            ctClass.detach();
            System.out.println("Instrumentation complete.");
        } catch (Throwable ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
        return byteCode;
    }
}
