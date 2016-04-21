package com.design.patterns.observer_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.observer_patterns
 * @date 2016/4/6 8:58
 */
public interface IObserve {
    void addObj(IListenner listenner);
    void removeObj(IListenner listenner);
    void notifyObj(String message);
}
