package com.jvm.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.jstack;
import static com.sun.btrace.BTraceUtils.println;

/**
 * 打印创建某个对象是某个方法触发
 * @author chendurex
 * @date 2018-11-17 23:10
 */
@BTrace
public class PrintNewObject {
    @OnMethod(clazz = "com.jvm.btrace.InvokableNewData",
            method = "/.*/",
            location = @Location(value = Kind.NEW,
                                clazz = "java.util.HashMap"))
    public static void newData(@ProbeClassName String className,
                                @ProbeMethodName String methodName) {
        println(className + "." + methodName);
        jstack();
        println();
    }
}
