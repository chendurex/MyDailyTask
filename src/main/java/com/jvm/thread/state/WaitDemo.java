package com.jvm.thread.state;

/**
 * @author chen
 * @date 2017/3/18 16:19
 */
public class WaitDemo {

    public static void main(String[] args) {
        final Object monitor = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "ThreadOne").start();
    }
}
