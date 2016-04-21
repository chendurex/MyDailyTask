package com.design.patterns.state_patterns;


/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.state_patterns
 * @date 2016/4/11 9:35
 */
public class StartState implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("start state");
    }
}
