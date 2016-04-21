package com.design.patterns.state_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.state_patterns
 * @date 2016/4/11 9:36
 */
public class Context {
    private State state;
    public Context(){}
    public void setState(State state){
        this.state = state;
    }
    public State getState() {
        return state;
    }
}
