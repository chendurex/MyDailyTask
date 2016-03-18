package com.design.patterns.factory_patterns;

/**
 * Created by LENOVO on 2016/3/14.
 */
public class FactoryTest {
    public static void main(String[] args) {
        ProducerFactory.getFactory("red").getColor().showColor();
    }
}
