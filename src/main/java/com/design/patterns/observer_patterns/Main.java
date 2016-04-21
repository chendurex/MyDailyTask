package com.design.patterns.observer_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.observer_patterns
 * @date 2016/4/6 9:08
 */
public class Main {
    public static void main(String[] args) {
        IListenner chat = new ChatListennerImpl();
        IListenner movie = new MovieListennerImpl();
        IListenner side = new SideListennerImpl();
        IObserve observe = new ObserveImpl();
        observe.addObj(chat);observe.addObj(side);observe.addObj(movie);
        observe.notifyObj("open");
        System.out.println("---------");
        observe.removeObj(chat);
        observe.notifyObj("close");
    }
}
