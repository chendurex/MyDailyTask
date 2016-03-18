package com.design.patterns.abstract_factory_patterns;

/**
 * Created by LCW009 on 2016/3/11.
 */
public class BlueColorImpl implements IColor{
    @Override
    public ColorEnum fill() {
        return ColorEnum.Blue;
    }
}
