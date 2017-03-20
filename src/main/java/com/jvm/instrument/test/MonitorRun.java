package com.jvm.instrument.test;

/**
 * @author chen
 * @description
 * @pachage com.jvm.instrument.test
 * @date 2016/05/22 22:06
 */
public class MonitorRun {
    public static void main(String[] args) throws InterruptedException {
        MonitorObj obj = new MonitorObj();
        obj.setName("chendurex");
        for(;;) {
            System.out.println(obj.show());
            Thread.sleep(20000);
        }
    }
}
