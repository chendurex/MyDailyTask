package com.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/8/20.
 */
public class DaemonThread {
    @Test
    public void test() throws Exception{
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        condition.wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "my_daemon_thread").start();

    }
}
