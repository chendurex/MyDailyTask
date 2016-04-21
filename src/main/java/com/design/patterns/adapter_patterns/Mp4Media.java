package com.design.patterns.adapter_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.adapter_patterns
 * @date 2016/3/22 9:05
 */
public class Mp4Media implements MediaAdvance{
    @Override
    public void playMp4() {
        System.out.println("this is mp4 media play");
    }

    @Override
    public void playTcl() {
        throw new RuntimeException("not support tcl file type");
    }
}
