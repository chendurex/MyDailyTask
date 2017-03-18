package com.jvm.thread.state;

/**
 * @author chen
 * @date 2017/3/18 15:03
 */
public class DeadLoopDemo {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // do nothing
                }
            }
        }, "deadLoopThread").start();
    }
}
