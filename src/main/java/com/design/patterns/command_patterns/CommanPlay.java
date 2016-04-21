package com.design.patterns.command_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.command_patterns
 * @date 2016/3/30 9:16
 */
public class CommanPlay implements Command {
    private Audio audio;
    public CommanPlay(Audio audio) {
        this.audio = audio;
    }
    @Override
    public void execute() {
        audio.play();
    }
}
