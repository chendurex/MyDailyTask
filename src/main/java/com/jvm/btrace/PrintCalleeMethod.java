package com.jvm.btrace;

import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;

/**
 * 打印某个类或者包或者整个程序中调用指定方法的堆栈
 * 使用场景：比如想知道某个方法是哪个方法触发的
 * @author chendurex
 * @date 2018-11-17 14:05
 */
@BTrace
public class PrintCalleeMethod {

    /**
     * 打印InvokableMetadata这个类中，调用了HashMap的get方法的某个方法
     * @param className 类名
     * @param probeMethod 方法名
     * @param targetMethod 被调用的方法名
     */
    @OnMethod(clazz = "com.jvm.btrace.InvokableMetadata",
            method = "/.*/",
            location = @Location(value = Kind.CALL,
                    clazz = "java.util.HashMap",
                    method = "get"))
    public static void hashMapGetCallee(@ProbeClassName String className,
                           @ProbeMethodName String probeMethod,
                           @TargetMethodOrField(fqn = true) String targetMethod) {
        println(className +"." + probeMethod + "-->" + targetMethod);
        // 打印堆栈信息
        jstack();
        println();
    }


    /**
     * 找到整个程序中执行System.gc()的调用堆栈
     */
    @OnMethod(clazz = "/.*/",
            method = "/.*/",
            location = @Location(value = Kind.CALL,
                    clazz = "java.lang.System",
                    method = "gc"))
    public static void systemGc(@ProbeClassName String className,
                           @ProbeMethodName String probeMethod,
                           @TargetMethodOrField(fqn = true) String targetMethod) {
        println(className +"." + probeMethod + "-->" + targetMethod);
        jstack();
        println();
    }
}
