package com.jdk.enums.strategy_pattern;

/**
 * Created by LENOVO on 2016/3/17.
 */
public class Match {
    public static void main(String args[]) {
        Player ctx = new Player(Strategy.T20);
        ctx.play();
        ctx.setStrategy(Strategy.ONE_DAY);
        ctx.play();
        ctx.setStrategy(Strategy.TEST);
        ctx.play();
    }

}
