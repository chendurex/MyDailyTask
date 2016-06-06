package com.jvm.javassist;

import javassist.bytecode.*;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.javassist
 * @date 2016/6/2 9:37
 */
public class CreateClass {
    public static void main(String[] args) throws Exception {
        ClassFile cf = new ClassFile(false, "com.jvm.javassist.Foo", "java.util.ArrayList");
        cf.setInterfaces(new String[] { "java.lang.Cloneable" });

        FieldInfo f = new FieldInfo(cf.getConstPool(), "width", "I");
        MethodInfo m = new MethodInfo(cf.getConstPool(),"test","(Ljava/lang/String;)Ljava/lang/String;");
        m.setAccessFlags(AccessFlag.PRIVATE);
        cf.addMethod(m);
        f.setAccessFlags(AccessFlag.PUBLIC);
        cf.addField(f);

        cf.write(new DataOutputStream(new FileOutputStream("Foo.class")));
    }
}
