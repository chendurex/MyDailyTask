package com.jdk.enums.strategy_pattern;

/**
 * Created by LENOVO on 2016/3/17.
 */
public class Player {
    private Strategy battingStrategy;
    public Player(Strategy battingStrategy){
        this.battingStrategy = battingStrategy;
    }
    public void setStrategy(Strategy newStrategy){
        this.battingStrategy = newStrategy;
    }
    public void play(){ battingStrategy.play(); }

}
