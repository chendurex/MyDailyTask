package com.jvm.btrace;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

import java.lang.reflect.Field;
import java.util.Map;

import static com.sun.btrace.BTraceUtils.*;

/**
 * 打印线上方法的入参
 * @author chendurex
 * @date 2018-11-17 13:44
 */
@BTrace
public class PrintArguments {
    private static final String CLZ = "com.jvm.btrace.InvokerPrintArguments";
    /**
     * 打印当前类中所有执行的方法
     * @param className 类名
     * @param probeMethodName 方法名
     * @param anyTypes 参数
     */
    // @OnMethod(clazz = "/com.jvm.btrace.*/", method = "/.*/")
    //@OnMethod(clazz = CLZ, method = "/.*/")
    public static void all(@ProbeClassName String className,
                           @ProbeMethodName String probeMethodName,
                           AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }

    /**
     * 如果集合中的类型是普通的类型(字符串、原始类型、包装类型)则会打印出具体值
     * 类似Arrays.toString()
     */
    @OnMethod(clazz = CLZ, method = "printCollection")
    public static void printCollection(@ProbeClassName String className,
                           @ProbeMethodName String probeMethodName,
                           AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }

    /**
     * @see #printCollection(String, String, AnyType[])
     */
    @OnMethod(clazz = CLZ, method = "printBoxedPrimitive")
    public static void printBoxedPrimitive(@ProbeClassName String className,
                                       @ProbeMethodName String probeMethodName,
                                       AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }

    /**
     * @see #printCollection(String, String, AnyType[])
     */
    @OnMethod(clazz = CLZ, method = "printPrimitive")
    public static void printPrimitive(@ProbeClassName String className,
                                       @ProbeMethodName String probeMethodName,
                                       AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        printArray(anyTypes);
    }


    /**
     * 打印复杂的对象，如果对象中的字段全部是基本类型，那么都可以打印出来
     * 如果对象中包含了子对象，那么需要通过获取子对象然后再重复打印
     * 如果对象中存在Map集合等，如果集合的实现是JDK版本，那么也能打印，否则无法打印
     * 如果想一次性把对象内容包括子对象内容都打印或者解除map集合限制，可以修改btrace代码
     * 或者通过阿里提供的更加高级的监控工具https://github.com/alibaba/arthas
     */
    @OnMethod(clazz = CLZ, method = "printableMetadata")
    public static void printableMetadata(@ProbeClassName String className,
                                      @ProbeMethodName String probeMethodName,
                                      AnyType[] anyTypes) {

        println(className + "." + probeMethodName);
        AnyType firstArgs = anyTypes[0];
        // 打印静态值
        println("-----print static value--------");
        printStaticFields(classOf(firstArgs));
        // 可以打印基本的类型域值
        printFields(firstArgs);
        println();
        // 打印子对象
        println("-----print stub--------");
        Field stub = field(classOf(firstArgs), "stub");
        Object stubV = get(stub, firstArgs);
        printFields(stubV);
        println();
        // 打印stub子对象中的对象
        println("-----print sub stub--------");
        Field subStub = field(classOf(stubV), "subStub");
        Object subStubV = get(subStub, stubV);
        printFields(subStubV);
        println();
        // 打印MAP，如果是JDK内部的Map，那么会将内部数据打印出来，但是如果是其它的Map如Guava的话，只打印对象出来
        // 这里解决办法只能是通过获取key获取数据了，或者修改btrace源码了(内部做了判断限制)
        // 或者通过阿里提供的https://github.com/alibaba/arthas工具
        println("-----print map--------");
        Field mapV = field(classOf(firstArgs), "mapV");
        Map map= (Map)get(mapV, firstArgs);
        printMap(map);
        println();
        println("-----print map table--------");
        // 如果是HashMap，也可以直接获取内部table，然后以数组形式打印
        Field table = field(classOf(map), "table");
        Object [] arrays = (Object[]) get(table, map);
        printArray(arrays);
        // 获取map集合中某个属性值
        println("-----print map filed--------");
        println(get(map, 1));
        //打印集合
        println("-----print list--------");
        Field lstub = field(classOf(firstArgs), "lstub");
        println(get(lstub, firstArgs));
        println();

        println("------------finish--------------");
    }

}
