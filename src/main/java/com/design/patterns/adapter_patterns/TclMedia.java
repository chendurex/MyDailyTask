package com.design.patterns.adapter_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.adapter_patterns
 * @date 2016/3/22 9:06
 */
public class TclMedia implements MediaAdvance {
    @Override
    public void playMp4() {
        throw new RuntimeException("not support mp4 file type");
    }

    @Override
    public void playTcl() {
        System.out.println("this is tcl play");
    }
}
