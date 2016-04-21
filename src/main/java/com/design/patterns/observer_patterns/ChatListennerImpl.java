package com.design.patterns.observer_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.observer_patterns
 * @date 2016/4/6 9:06
 */
public class ChatListennerImpl implements IListenner {
    @Override
    public void openSide() {
        System.out.println("I'm reading open chat view");
    }

    @Override
    public void closeSide() {
        System.out.println("I'm reading close chat view");
    }
}
