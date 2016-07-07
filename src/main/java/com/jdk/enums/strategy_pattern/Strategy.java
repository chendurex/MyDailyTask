package com.jdk.enums.strategy_pattern;

/**
 * Created by LENOVO on 2016/3/17.
 */
public enum Strategy {
    /* Make sure to score quickly on T20 games */
    T20 {
        @Override public void play() {
            System.out.printf("In %s, If it's in the V, make sure it goes to tree %n", name());
        }
    }, /* Make a balance between attach and defence in One day */
    ONE_DAY {
        @Override public void play() {
            System.out.printf("In %s, Push it for Single %n", name());
        }
    }, /* MonitorAssign match is all about occupying the crease and grinding opposition */
    TEST {
        @Override public void play() {
            System.out.printf("In %s, Grind them hard %n", name());
        }
    };
    public void play() {
        System.out.printf("In Cricket, Play as per Merit of Ball %n");
    }

}
