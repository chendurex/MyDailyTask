package com.design.patterns.front_controller_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.front_controller_patterns
 * @date 2016/4/18 9:26
 */
public class Dispatcher {
    private DispatcherView dispatcherView;
    public void doDispatcher(String name) {
        if (name.contains("teacher")) {
            dispatcherView = new TeacherView();
        } else {
            dispatcherView = new StudentView();
        }
        dispatcherView.show();
    }
}
