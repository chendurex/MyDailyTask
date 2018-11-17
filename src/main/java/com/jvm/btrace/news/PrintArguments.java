package com.jvm.btrace.news;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

import static com.sun.btrace.BTraceUtils.printArray;
import static com.sun.btrace.BTraceUtils.println;

/**
 * 打印线上方法的入参
 * @author chendurex
 * @date 2018-11-17 13:44
 */
@BTrace
public class PrintArguments {
    private static final String CLZ = "com.jvm.btrace.news.InvokerPrintArguments";

    /**
     * 打印当前类中所有执行的方法
     * @param className
     * @param probeMethodName
     * @param anyTypes
     */
    @OnMethod(clazz = CLZ, method = "/.*/")
    public static void all(@ProbeClassName String className,
                           @ProbeMethodName String probeMethodName,
                           AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }

    //@OnMethod(clazz = CLZ, method = "printCollection")
    public static void printCollection(@ProbeClassName String className,
                           @ProbeMethodName String probeMethodName,
                           AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }

    //@OnMethod(clazz = CLZ, method = "printBoxedPrimitive")
    public static void printBoxedPrimitive(@ProbeClassName String className,
                                       @ProbeMethodName String probeMethodName,
                                       AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }

    //@OnMethod(clazz = CLZ, method = "printPrimitive")
    public static void printPrimitive(@ProbeClassName String className,
                                       @ProbeMethodName String probeMethodName,
                                       AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }

    //@OnMethod(clazz = CLZ, method = "printableMetadata")
    public static void printableMetadata(@ProbeClassName String className,
                                      @ProbeMethodName String probeMethodName,
                                      AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }
}
