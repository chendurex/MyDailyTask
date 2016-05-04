package com.jvm.btrace.unsafe_mode;

import com.jvm.btrace.BtraceData;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

import java.util.Arrays;
import java.util.List;

import static com.sun.btrace.BTraceUtils.*;

/**
 * 开启unsafe模式，创建对象，数组、捕获异常，抛出异常限制都没了
 */
@BTrace(unsafe = true)
public class CreateObjectDemo {
    private static String field = "field enter";
    @OnMethod(clazz = "com.jvm.btrace.BtraceData",
                method = "/.*/")
    public static void run(@ProbeClassName String className,
                    @ProbeMethodName String methodName) throws Exception{
        BtraceData btraceData = new BtraceData();
        btraceData.setAge(1);
        btraceData.setName("name");
        btraceData.setGender("male");
        println(Strings.concat(className,Strings.concat(".",Strings.concat(methodName,Strings.concat("---obj-->",btraceData.toString())))));
        List<Integer> list = Arrays.asList(1,2,3);
        say();
        try {
            String error = "a";
            Integer.parseInt(error);
        } catch (Exception e) {
            throw new Exception("error");
        }
    }

    public static void say() {
        print(field);
        println("call enter");
    }
}
