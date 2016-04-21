package com.design.patterns.command_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.command_patterns
 * @date 2016/3/30 9:18
 */
public class Main {
    public static void main(String[] args) {
        Command commandPlay = new CommanPlay(new Audio());
        Command commandStop = new CommanStop(new Audio());
        KeyBoard keyBoard = new KeyBoard();
        keyBoard.setPlayCommand(commandPlay);
        keyBoard.setStopCommand(commandStop);
        keyBoard.enterPlayKey();
        keyBoard.enterStopKey();
    }
}
