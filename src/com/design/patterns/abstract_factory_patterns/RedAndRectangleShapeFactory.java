package com.design.patterns.abstract_factory_patterns;

/**
 * Created by LENOVO on 2016/3/14.
 */
public class RedAndRectangleShapeFactory implements IFactory {
    @Override
    public IColor getColor() {
        return new RedColorImpl();
    }

    @Override
    public IShape getShape() {
        return new RectangleShapeImpl();
    }
}
