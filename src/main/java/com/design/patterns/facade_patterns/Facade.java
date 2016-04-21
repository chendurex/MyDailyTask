package com.design.patterns.facade_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.facade_patterns
 * @date 2016/3/31 9:34
 */
public class Facade implements IFacade {
    private Shape circleShape;
    private Shape rectangleShape;
    private Shape squareShape;

    public Facade() {
        this.circleShape = new Circle();
        this.rectangleShape = new Rectangle();
        this.squareShape = new Square();
    }
    @Override
    public void drawCircle() {
        circleShape.draw();
    }

    @Override
    public void drawSquare() {
        squareShape.draw();
    }

    @Override
    public void drawRectangle() {
        rectangleShape.draw();
    }
}
