package com.design.patterns.abstract_factory_patterns;

/**
 * Created by LCW009 on 2016/3/11.
 */
public class RectangleShapeImpl implements IShape{
    @Override
    public ShapeEnum draw() {
        return ShapeEnum.Rectangle;
    }
}
