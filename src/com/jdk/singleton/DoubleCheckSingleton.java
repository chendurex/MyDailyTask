package com.jdk.singleton;


/**
 * Created by LENOVO on 2016/3/15.
 */
//双重检查
public class DoubleCheckSingleton {
    private static  volatile DoubleCheckSingleton singleton = null ;
    private DoubleCheckSingleton() {}

    public static DoubleCheckSingleton getInstance() {
        if (singleton == null) {
            synchronized (DoubleCheckSingleton.class) {
                if(singleton == null) {
                    singleton = new DoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }

}
