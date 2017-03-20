package com.jvm.instrument;

/**
 * @author chen
 * @description
 * @pachage com.jvm.instrument
 * @date 2016/05/22 22:06
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class ClassWriter implements ClassFileTransformer {
    private Instrumentation inst;

    protected ClassWriter(Instrumentation inst) {
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
        System.out.println(className);
        if (!className.contains("com/spring/boot/demo/")) {
            return classfileBuffer;
        }
        try {
            File file = new File("d:\\class\\"+ className.substring(className.lastIndexOf("/") + 1) + ".class");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(classfileBuffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
