package com.jvm.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

/**
 * 监控某个方法的返回值，注意，千万慎用匹配模式，只能调用带返回值的方法，如果方法不带返回值则会阻塞程序，并且后端程序抛异常
 */
@BTrace
public class ReturnDemo {
    @OnMethod(clazz = "com.jvm.btrace.BtraceData",
            method = "toString",
            location = @Location(value = Kind.RETURN))
    public static void run(@ProbeClassName String className,
                           @ProbeMethodName String probeMethodName,
                           //@TargetMethodOrField(fqn = true) String targetMethod,不能使用打印详细方法信息的注解，因为次注解只支持Kind.CALL, Kind.FIELD_GET and Kind.FIELD_SET
                           @Return Object obj) {
        println(Strings.concat(className,Strings.concat(".",Strings.concat(probeMethodName,Strings.concat("--->return value:",Strings.str(obj))))));
    }
}
