package com.design.patterns.state_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.state_patterns
 * @date 2016/4/11 9:37
 */
public class Main {
    public static void main(String[] args) {
        State startState = new StartState();
        Context context = new Context();
        startState.doAction(context);
    }
}
