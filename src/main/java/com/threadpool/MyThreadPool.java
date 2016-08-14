package com.threadpool;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/8/14.
 */
public class MyThreadPool {
    private int coreThread = 5;
    private int maxThread = 20;

    @Test
    public void createFixedThread() throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(coreThread, maxThread, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5), new MyThreadFactory());
        for (int i = 10; i < 30; i++) {
            final int second = i;
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(second);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        TimeUnit.SECONDS.sleep(40);
        System.out.println(poolExecutor.getCompletedTaskCount());
    }
}
