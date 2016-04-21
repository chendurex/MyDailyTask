package com.design.patterns.abstract_factory_patterns;

/**
 * Created by LCW009 on 2016/3/11.
 */
public  class BlueColorAndCicleShapeFactory implements IFactory{
    @Override
    public IColor getColor() {
        return new BlueColorImpl();
    }

    @Override
    public IShape getShape() {
        return new CicleShapeImpl();
    }
}
