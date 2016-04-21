package com.design.patterns.adapter_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.adapter_patterns
 * @date 2016/3/22 9:02
 */
public class AudioPlay implements Media{
    private MediaAdapter adapter = new MediaAdapter() ;

    @Override
    public void play(String type, String fileName) {
        if (type.equals("mp3")) {
            System.out.println("this is mp3 play");
        } else if(type.equals("mp4")||type.equals("tcl")) {
            adapter.play(type,fileName);
        } else {
            throw new RuntimeException("not support type");
        }
    }
}
