package com.design.patterns.command_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.command_patterns
 * @date 2016/3/30 9:19
 */
public class KeyBoard {
    private Command playCommand;
    private Command stopCommand;

    public void setPlayCommand(Command command) {
        this.playCommand = command;
    }
    public void setStopCommand(Command command) {
        this.stopCommand = command;
    }
    public void enterPlayKey() {
        playCommand.execute();
    }
    public void enterStopKey() {
        stopCommand.execute();
    }
}
