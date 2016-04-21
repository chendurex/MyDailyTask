package com.design.patterns.bridge_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.bridge_patterns
 * @date 2016/3/24 9:29
 */
public class Main {
    public static void main(String[] args) {
        Shape shape = new RedCircle(new GreenDraw());
        shape.draw();
        Shape shape2 = new RedCircle(new RedDraw());
        shape2.draw();
    }
}
