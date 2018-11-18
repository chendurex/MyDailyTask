package com.jvm.arthas;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chendurex
 * @date 2018-11-18 15:22
 */
public class TraceMonitor {

    /**
     * 监控one方法内部耗时情况，计算出来two方法耗时3100ms，three方法1350ms
     * 此功能只能跟踪一级方法的调用链路
     * trace com.jvm.arthas.TraceMonitor one
     * 还可以进一步过滤耗时大于4600ms时才显示调用栈
     * trace com.jvm.arthas.TraceMonitor one #cost>4600
     */
    private static void one() {
        two();
        three();
    }

    /**
     * 如果方法有重载，则可以通过设置参数长度进行过滤
     * trace com.jvm.arthas.TraceMonitor one params.length==1
     */
    private static void one(String param) {
        two();
        three();
        four();
    }

    /**
     * 如果方法有重载，则可以通过设置参数长度进行过滤或者增加参数类型
     * trace com.jvm.arthas.TraceMonitor one 'params.length==1 && (params[0] instanceof java.util.Map)'
     * trace com.jvm.arthas.TraceMonitor one 'params.length==1 and params[0] instanceof java.util.Map'
     */
    private static void one(Map<Integer, Integer> param) {
        two();
        three();
        four();
        five();
    }

    private static void two() {
        try {
            TimeUnit.SECONDS.sleep(3);
            four();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void three() {
        try {
            TimeUnit.SECONDS.sleep(1);
            five();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void four() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void five() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
            six();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void six() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        for (; ; ) {
            one();
            one("1");
            one(ImmutableMap.of(3,3));
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
