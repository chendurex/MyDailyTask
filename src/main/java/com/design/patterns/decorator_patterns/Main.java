package com.design.patterns.decorator_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.decorator_patterns
 * @date 2016/3/31 9:25
 */
public class Main {
    public static void main(String[] args) {
        Shape shape1 = new Circle();
        Shape shape2 = new Rectangle();
        Shape shape3 = new CircleShapeDecorator(new Rectangle());
        shape1.draw();shape2.draw();shape3.draw();
    }
}
