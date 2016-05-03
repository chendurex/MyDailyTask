package com.jvm.btrace;

import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;

/**
 * TargetMethodOrField 注解打印方法内部调用某个方法的详细信息
 */
@BTrace
public class TargetMethodOrFieldDemo {
    @OnMethod(clazz = "com.jvm.btrace.BtraceData",
            method = "/.*/",
            location = @Location(value = Kind.CALL,
                    clazz = "java.util.HashMap",
                    method = "get"))
    public static void run(@ProbeClassName String className,
                           @ProbeMethodName String probeMethod,
                           @TargetMethodOrField(fqn = true) String targetMethod) {
        println(Strings.concat(className, Strings.concat(".",Strings.concat(probeMethod,Strings.concat("-->",targetMethod)))));
    }
}
