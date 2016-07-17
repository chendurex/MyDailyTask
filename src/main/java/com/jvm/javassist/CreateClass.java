package com.jvm.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.*;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

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

    @Test
    public void addVarargs() throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        CtClass cf = classPool.get("com.jvm.javassist.CtClassObject");
        CtMethod m = CtMethod.make("public int length(int [] args) { return args.length; }",cf);
        // 貌似没生效，不能使用可变数组
        m.setModifiers(m.getModifiers() | Modifier.VARARGS);
        cf.addMethod(m);
        CtClassObject ctClassObject = (CtClassObject) cf.toClass().newInstance();
        // 怎样使用javassist执行方法？
        CtMethod ctMethod = cf.getDeclaredMethod("length");
        Method method = ctClassObject.getClass().getDeclaredMethod("length",new Class[]{int[].class});
        System.out.println(method.invoke(ctClassObject,new int[]{123,123,1234}));

    }
}
