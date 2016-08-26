package com.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/8/20.
 */
public class DaemonThread extends Thread {

    public static void main(String[] args) {
        DaemonThread threadTest  = new DaemonThread();
        threadTest.setDaemon(true);
        threadTest.start();
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
