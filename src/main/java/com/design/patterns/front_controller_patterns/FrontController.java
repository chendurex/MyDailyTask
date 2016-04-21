package com.design.patterns.front_controller_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.front_controller_patterns
 * @date 2016/4/18 9:25
 */
public class FrontController {
    private Dispatcher dispatcher;
    public FrontController(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    private boolean isAuthenticUser(){
        System.out.println("User is authenticated successfully.");
        return true;
    }

    private void trackRequest(String request){
        System.out.println("Page requested: " + request);
    }
    public void dispatcherRequest(String name) {
        trackRequest(name);
        if (isAuthenticUser()) {
            dispatcher.doDispatcher(name);
        }
    }
}
