package com.design.patterns.facade_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.facade_patterns
 * @date 2016/3/31 9:42
 */
public class Main {
    public static void main(String[] args) {
        IFacade facade = new Facade();
        facade.drawCircle();
        facade.drawRectangle();
        facade.drawSquare();
    }
}
