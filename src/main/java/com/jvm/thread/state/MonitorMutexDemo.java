package com.jvm.thread.state;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2017/3/18 15:25
 */
public class MonitorMutexDemo {
    public static void main(String[] args) {
        final Object monitor = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (monitor) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            monitor.notifyAll();
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "threadOne").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (monitor) {
                        try {
                            TimeUnit.SECONDS.sleep(2);
                            monitor.notifyAll();
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "threadTwo").start();
    }
}
