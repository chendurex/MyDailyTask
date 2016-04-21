package com.design.patterns.abstract_factory_patterns;

/**
 * Created by LENOVO on 2016/3/14.
 */
public class FactoryPruducer {
    public static IFactory getFactory(String name) {
        IFactory factory = null;
        switch (name) {
            case "red" : factory = new RedAndRectangleShapeFactory();
                break;
            case "blue": factory = new BlueColorAndCicleShapeFactory();
                break;
            default: factory = new RedAndRectangleShapeFactory();
        }
        return factory;
    }
}
