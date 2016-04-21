package com.jdk.enums.implements_interface;

/**
 * Created by LENOVO on 2016/3/17.
 */
public enum ImplementsInterface implements Runnable{
    RUN;

    @Override
    public void run() {
        System.out.println("enter");
    }

    public static void main(String[] args) {
        new Thread(ImplementsInterface.RUN).start();
    }
}
