package com.jvm.instrument.test;

/**
 * Created by Administrator on 2016/5/29.
 */
public class MonitorRun {
    public static void main(String[] args) throws InterruptedException {
        MonitorObj obj = new MonitorObj();
        obj.setName("chendurex");
        while (true) {
            System.out.println(obj.show());
            obj.show();
            Thread.sleep(20000);
        }
    }
}
