package com.jdk.singleton;

/**
 * Created by LENOVO on 2016/3/15.
 */
//内部类创建
public class InnerClassSingleton {
    private InnerClassSingleton(){}
    private static class SingletonHolder  {
        private static final InnerClassSingleton innerSingleton = new InnerClassSingleton();
    }
    public static final InnerClassSingleton getInstance() {
        return SingletonHolder .innerSingleton;
    }
}
