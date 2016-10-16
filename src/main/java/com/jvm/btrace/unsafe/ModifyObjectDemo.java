package com.jvm.btrace.unsafe;

import com.jvm.btrace.BtraceData;
import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

import java.util.Arrays;
import java.util.List;

import static com.sun.btrace.BTraceUtils.*;

/**
 * 开启unsafe模式，创建对象，数组、捕获异常，抛出异常限制都没了
 */
@BTrace(unsafe = true)
public class ModifyObjectDemo {
    @OnMethod(clazz = "com.jvm.btrace.BtraceData",
                method = "setThis")
    public static void run(@ProbeClassName String className,
                    @ProbeMethodName String methodName,
                           AnyType[] anyTypes) throws Exception{
        println("current className is:" + className +
                ",and methodName isL" + methodName +
                ",and argument is:" + anyTypes);
        if (anyTypes.length == 1 && anyTypes[0] instanceof BtraceData) {
            BtraceData btraceData = (BtraceData)anyTypes[0];
            btraceData.setAge(1);
            btraceData.setName("name");
            btraceData.setGender("male");
            println(Strings.concat(className,Strings.concat(".",
                    Strings.concat(methodName,Strings.concat("---obj-->",btraceData.toString())))));
        }
    }
}
