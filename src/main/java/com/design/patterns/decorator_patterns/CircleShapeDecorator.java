package com.design.patterns.decorator_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.decorator_patterns
 * @date 2016/3/31 9:17
 */
public class CircleShapeDecorator extends ShapeDecorator {
    public CircleShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        super.draw();
        setBounder();
    }

    public void setBounder() {
        System.out.println("red bounder");
    }
}
