package com.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen
 * @description
 * @pachage com.threadpool
 * @date 2016/8/11 19:12
 */
public class Main {
    public static void main(String[] args) {
        int coreThread = 1;
        int threads = 100;
        int queues = 0;
        final  AtomicInteger atomicInteger = new AtomicInteger();
        String name = "thread-test";
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(coreThread, threads, 0L, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue() : (queues < 0 ? new LinkedBlockingQueue() : new LinkedBlockingQueue(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));

        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("current task = " + atomicInteger.incrementAndGet() + "==================");
                    try {
                        TimeUnit.SECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("core pool size = " + threadPoolExecutor.getCorePoolSize());
            System.out.println("core max size = " + threadPoolExecutor.getMaximumPoolSize());
            System.out.println("core active size = " + threadPoolExecutor.getActiveCount());
            System.out.println("core task size = " + threadPoolExecutor.getTaskCount());
        }
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("core pool size = " + threadPoolExecutor.getCorePoolSize());
        System.out.println("core max size = " + threadPoolExecutor.getMaximumPoolSize());
        System.out.println("core active size = " + threadPoolExecutor.getActiveCount());
        System.out.println("core task size = " + threadPoolExecutor.getTaskCount());
    }
}
