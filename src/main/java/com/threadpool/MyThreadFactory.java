package com.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/8/14.
 */
public class MyThreadFactory implements ThreadFactory {
    private AtomicInteger poolSEQ = new AtomicInteger(1);
    private String prefix = "myThreadTest-";
    private final ThreadGroup mGroup;

    public MyThreadFactory() {
        this.mGroup = Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        preCreate();
        Thread thread = new Thread(r, getThreadName());
        afterCreate();
        return thread;
    }

    private String getThreadName() {
        return prefix + poolSEQ.getAndIncrement();
    }

    public void preCreate() {
        System.out.println(prefix + poolSEQ.get() + "   creating");
    }

    public void afterCreate() {
        System.out.println(prefix + poolSEQ.get()+ "  created");
    }
}
