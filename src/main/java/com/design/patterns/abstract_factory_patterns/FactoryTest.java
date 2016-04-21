package com.design.patterns.abstract_factory_patterns;

/**
 * Created by LENOVO on 2016/3/14.
 */
public class FactoryTest {
    public static void main(String[] args) {
        IFactory factory = FactoryPruducer.getFactory("red");
        System.out.println("color is :" + factory.getColor().fill()+", and shape is :"+factory.getShape().draw());

        IFactory factory2 = FactoryPruducer.getFactory("blue");
        System.out.println("color is :" + factory2.getColor().fill()+", and shape is :"+factory2.getShape().draw());
    }
}
