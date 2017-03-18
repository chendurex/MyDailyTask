package com.jvm.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2017/3/18 18:47
 */
public class DeadLockDemo {
    final static Object mutexOne = new Object();
    final static Object mutexTwo = new Object();

    public static void main(String[] args) {
        Thread threadOne = new Thread(new ThreadOne(), "threadOne");
        Thread threadTwo = new Thread(new ThreadTwo(), "threadTwo");
        threadOne.start();
        threadTwo.start();
    }
}

 class ThreadOne implements Runnable {
    @Override
    public void run() {
        synchronized (DeadLockDemo.mutexOne) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (DeadLockDemo.mutexTwo) {
                System.out.println("one enter ");
            }
        }
    }
}

class ThreadTwo implements Runnable {
    @Override
    public void run() {
        synchronized (DeadLockDemo.mutexTwo) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (DeadLockDemo.mutexOne) {
                System.out.println("two enter ");
            }
        }
    }
}
