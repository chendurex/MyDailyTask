package com.jvm.btrace;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.printFields;
import static com.sun.btrace.BTraceUtils.println;

/**
 * 监控某个方法的返回值
 * 注意，千万慎用匹配模式，只能调用带返回值的方法，如果方法不带返回值则会阻塞程序，并且后端程序抛异常
 * @author chendurex
 * @date 2018-11-17 22:05
 */
@BTrace
public class PrintReturnValue {
    /**
     * 打印返回对象
     * 如果需要详细打印对象内容，可以参考{@link PrintArguments#printableMetadata(String, String, AnyType[])}
     * @param obj 返回值，如果确定某个返回值是jdk内部值，则可以直接指定，否则使用object
     *            当然也可以使用具体的对象，那就需要指定classpath，但是作用不大，因为不能直接调用对象
     */
    @OnMethod(clazz = "com.jvm.btrace.InvokableMetadata",
            //method = "/getThis|getName/",
            method = "/get(This|Name)/",
            //method = "/getThis.*|ge.*ame/",
            //method = "/getThis.*|ge[a-z]Name/",
            location = @Location(value = Kind.RETURN))
    public static void getObject(@ProbeClassName String className,
                           @ProbeMethodName String probeMethodName,
                           @Return Object obj) {
        println(className + "." + probeMethodName);
        printFields(obj);
        println();
    }
}
