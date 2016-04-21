package com.design.patterns.adapter_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.adapter_patterns
 * @date 2016/3/22 9:16
 */
public class Main {
    public static void main(String[] args) {
        Media media = new AudioPlay();
        media.play("mp3",null);
        media.play("mp4","mp4");
        media.play("tcl","tcl");
    }
}
