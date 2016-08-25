package com.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/8/20.
 */
public class DaemonThread {
    @Test
    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("enter");
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "my_daemon_thread").start();
    }
}
