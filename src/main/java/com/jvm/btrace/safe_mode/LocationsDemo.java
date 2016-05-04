package com.jvm.btrace.safe_mode;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

/**
 * 利用locations注解配合参数监控
 * value : Kind枚举
 * class : 监控的类，支持全限定名称与正则 ，注意类名必须是声明的类名而不是实际的对象类名
 * method : 监控的方法名称，支持全限定名与正则
 */
@BTrace
public class LocationsDemo {
    /**
     * 监控BtraceData类中某个方法调用了HashMap类中的get方法
     * @param className
     * @param probeMethod
     */
    @OnMethod(clazz = "com.jvm.btrace.BtraceData",
            method = "/.*/",
            location = @Location(value = Kind.CALL,
                    clazz = "java.util.HashMap",
                    method = "get"))
    public static void run(@ProbeClassName String className,
                           @ProbeMethodName String probeMethod) {
        println(Strings.concat(className,Strings.concat(".",probeMethod)));
    }

}
