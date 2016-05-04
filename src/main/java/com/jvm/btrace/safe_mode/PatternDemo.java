package com.jvm.btrace.safe_mode;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

import static com.sun.btrace.BTraceUtils.*;

/**
 * 正则监控某个包下的所有方法
 */
@BTrace
public class PatternDemo {
    @OnMethod(clazz = "/com.jvm.btrace.*/",
            method = "/.*/")
    public static void run(@ProbeClassName String className,
                           @ProbeMethodName String probeMethod) {
        println(Strings.concat(className,Strings.concat(".",probeMethod)));
    }

}
