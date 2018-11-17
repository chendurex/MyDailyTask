package com.jvm.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.println;

/**
 * 打印执行对象内容
 * @author chendurex
 * @date 2018-11-17 23:08
 */
@BTrace
public class PrintTargetObjectValue {

    /**
     * 打印当前方法对象
     * 这里是打印InvokableMetadata这个对象值
     * @param me
     */
    @OnMethod(clazz = "com.jvm.btrace.InvokableMetadata",
            method = "run")
    public static void printCurrentObj(@ProbeClassName String className,
                                       @ProbeMethodName String methodName,
                                       @Self Object me) {
        println(className + "." + methodName);
        println(me);
        println();
    }

    /**
     * 打印执行内部调用对象
     * 比如getName方法内部调用了HashMap中的方法，那么target为HashMap对象
     * @param target 目标对象
     */
    @OnMethod(clazz = "com.jvm.btrace.InvokableMetadata",
            method = "/get.*/", // 使用匹配模式
            location = @Location(value = Kind.CALL,
                                clazz = "java.util.HashMap",
                                method = "/.*/"))
    public static void printInnerCallee(@ProbeClassName String className,
                                       @ProbeMethodName String methodName,
                                       @TargetInstance Object target) {
        println(className + "." + methodName);
        println(target);
        println();
    }
}
