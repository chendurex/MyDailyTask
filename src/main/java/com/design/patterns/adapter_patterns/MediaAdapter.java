package com.design.patterns.adapter_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.adapter_patterns
 * @date 2016/3/22 9:08
 */
public class MediaAdapter implements Media {
    private MediaAdvance mediaAdvance = null;
    @Override
    public void play(String type, String fileName) {
        if (type.equals("mp4")) {
            mediaAdvance = new Mp4Media();
            mediaAdvance.playMp4();
        } else if (type.equals("tcl")) {
            mediaAdvance = new TclMedia();
            mediaAdvance.playTcl();
        } else {
            System.out.println("this type is not support");
        }
    }
}
