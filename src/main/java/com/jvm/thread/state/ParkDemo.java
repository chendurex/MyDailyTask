package com.jvm.thread.state;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chen
 * @date 2017/3/18 17:19
 */
public class ParkDemo {

    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.tryLock(25, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {

                }
            }
        }, "threadOne").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.tryLock(25, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {

                }
            }
        }, "threadTwo").start();
    }
}
