package com.design.patterns.factory_patterns;

/**
 * Created by LENOVO on 2016/3/14.
 */
public class ProducerFactory {
    public static ColorFactory getFactory(String color) {
        ColorFactory factory = null;
        switch (color) {
            case "red" : factory = new RedFactoryImpl();break;
            case "blue" : factory = new BlueFactoryImpl();break;
            default:factory = new BlueFactoryImpl();break;
        }
        return factory;
    }
}
