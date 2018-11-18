package com.jvm.arthas;

import java.util.concurrent.TimeUnit;

/**
 * 监控方法执行情况，
 * @author chendurex
 * @date 2018-11-18 14:55
 */
public class UsefullTools {

    /**
     * 每隔5s监控这个方法调用情况，比如在监控某个调用链路响应时间特别长的时候或者偶先调用失败的时候比较有用
     * monitor -c 5 com.jvm.arthas.UsefullTools run
     * @param v
     */
    public static void run(String v) {
        System.out.println(v);
    }

    /**
     * 打印调用当前方法的调用路径，类似btrace的jstack功能
     * stack com.jvm.arthas.UsefullTools callTrace
     * 或者可以进一步过滤参数内容
     * stack com.jvm.arthas.UsefullTools callTrace 'params[0]==""'
     * @param v
     */
    public static void callTrace(String v) {

    }

    public static void main(String[] args) throws Exception {
        for (; ; ) {
            run("hello world");
            callTrace("");
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * 反编译某个类，比如发现某个方法执行的代码可能不是最新的时候，特别有用
     * jad com.jvm.arthas.UsefullTools
     */
    public static void recompilier() {

    }
}
