package com.design.patterns.decorator_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.decorator_patterns
 * @date 2016/3/31 9:14
 */
public abstract class ShapeDecorator implements Shape{
    private Shape shape;
    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }
    @Override
    public void draw() {
        shape.draw();
    }
}
