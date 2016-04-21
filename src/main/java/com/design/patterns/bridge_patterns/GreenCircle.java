package com.design.patterns.bridge_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.bridge_patterns
 * @date 2016/3/24 9:25
 */
public class GreenCircle implements Shape {
    private DrawApi drawApi;
    public GreenCircle(DrawApi drawApi) {
        this.drawApi = drawApi;
    }
    @Override
    public void draw() {
        drawApi.drawApi();
    }
}
