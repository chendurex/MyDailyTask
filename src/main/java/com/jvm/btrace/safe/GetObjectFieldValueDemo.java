package com.jvm.btrace.safe;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.*;
import java.lang.reflect.Field;
import java.util.Map;

import static com.sun.btrace.BTraceUtils.*;

/**
 * 获取对象中某个field域的值
 */
@BTrace
public class GetObjectFieldValueDemo {
    @OnMethod(clazz = "com.jvm.btrace.BtraceData",
            method = "copy",
            location = @Location(value = Kind.RETURN))
    public static void run(@ProbeClassName String className,
                           @ProbeMethodName String probeMethodName,
                           AnyType[] anyTypes,
                           @Self Object self,
                           @Return Object res) {
        println(className + "." + probeMethodName);
        print("args:");
        printArray(anyTypes);
        print("name field :");
        Field name = field(classOf(res), "name");
        println(get(name, res));

        // 打印map对象里面的数据
        print("map field :");
        Field map = field(classOf(res), "map");
        Map map2 = (Map)get(map, res);
        println(map2);

        // 从map获取数据
        print("get value from map :");
        get(map2, "hehe");
        print("table field :");
        Field table = field(classOf(map2), "table");
        Object [] arrays = (Object[]) get(table, map2);
        println(arrays);

        println(self);
        println("finished");
    }
}
