package com.design.patterns.bridge_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.bridge_patterns
 * @date 2016/3/24 9:27
 */
public class RedDraw implements DrawApi {
    @Override
    public void drawApi() {
        System.out.println("this is red draw");
    }
}
