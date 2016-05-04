package com.jvm.btrace.safe_mode;

import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;
/**
 * 最基本监控某个类的某个方法，打印监控的类名和方法名称
 */
@BTrace
public class BaseDemo {
    @OnMethod(clazz = "com.jvm.btrace.BtraceData",
            method = "getName")
    public static void run(@ProbeClassName String className,
                           @ProbeMethodName String probeMethod) {
        println(Strings.concat(className,Strings.concat(".",probeMethod)));
    }

}
