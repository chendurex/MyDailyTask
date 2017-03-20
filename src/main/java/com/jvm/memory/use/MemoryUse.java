package com.jvm.memory.use;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.check_of_memory_use
 * @date 2016/5/19 22:35
 */
public class MemoryUse {
    private static final int MB = 1024 * 1024;
    private static final byte[] BUFF = new byte[1024 * 1024];

    private static final Map<Integer, byte[]> MAP = new HashMap<>(10000);

    public static void main(String[] args) throws Exception {
        System.out.println(args.length);
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        System.out.println("-------------开始统计内存使用量----------------");
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间：" + sdf.format(new Date()) + "当前总内存：" + runtime.totalMemory() / MB + "MB，空闲内容：" + runtime.freeMemory() / MB + "MB，使用内存：" + (runtime.totalMemory() / MB - runtime.freeMemory() / MB+"MB"));
        for (int i = 0; i < 100000; i++) {
            MAP.put(i, new byte[1024 * 1024]);
            System.out.println("当前时间：" + sdf.format(new Date()) + "当前总内存：" + runtime.totalMemory() / MB + "MB，空闲内容：" + runtime.freeMemory() / MB + "MB，使用内存：" + (runtime.totalMemory() / MB - runtime.freeMemory() / MB+"MB"));
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
