package com.design.patterns.observer_patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.observer_patterns
 * @date 2016/4/6 9:01
 */
public class ObserveImpl implements IObserve {
    private List<IListenner> listennerList = new ArrayList<>();

    @Override
    public void addObj(IListenner listenner) {
        listennerList.add(listenner);
    }

    @Override
    public void removeObj(IListenner listenner) {
        listennerList.remove(listenner);
    }

    @Override
    public void notifyObj(String message) {
        if (message.equals("open")) {
            for (IListenner iListenner : listennerList) {
                iListenner.openSide();
            }
        } else {
            for (IListenner iListenner : listennerList) {
                iListenner.closeSide();
            }
        }

    }
}
