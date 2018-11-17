package com.jvm.btrace;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author chendurex
 * @date 2018-11-18 00:16
 */
public class InvokableNewData {

    private void newData() {
        System.out.println(new HashMap<>(1));
    }

    public static void main(String[] args) throws Exception {
        for (; ; ) {
            new InvokableNewData().newData();
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
