package com.jvm.btrace.safe;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.*;

/**
 * 打印方法传入的参数，这个不能与返回值一起使用
 */
@BTrace
public class ArgmentsDemo {
    @OnMethod(clazz = "com.jvm.btrace.BtraceData",
            method = "/.*/",
            location = @Location(value = Kind.ENTRY))
    public static void run(@ProbeClassName String className,
                           @ProbeMethodName String probeMethodName,
                           AnyType [] anyTypes
                           ) {

        println(Strings.concat(className,Strings.concat(".",probeMethodName)));
        printArray(anyTypes);
    }
}
