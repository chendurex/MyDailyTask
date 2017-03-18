package com.jvm.thread.state;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chen
 * @date 2017/3/18 17:53
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor exe = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        exe.prestartAllCoreThreads();
        while (true) {
        }
    }
}
