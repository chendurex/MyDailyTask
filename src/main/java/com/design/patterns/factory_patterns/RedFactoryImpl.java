package com.design.patterns.factory_patterns;

/**
 * Created by LENOVO on 2016/3/14.
 */
public class RedFactoryImpl implements ColorFactory{
    @Override
    public IColor getColor() {
        return new Red();
    }
}
