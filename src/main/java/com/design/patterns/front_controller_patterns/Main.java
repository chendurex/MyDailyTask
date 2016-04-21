package com.design.patterns.front_controller_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.front_controller_patterns
 * @date 2016/4/18 9:34
 */
public class Main {
    public static void main(String[] args) {
        FrontController frontController = new FrontController(new Dispatcher());
        frontController.dispatcherRequest("teacher");
        frontController.dispatcherRequest("student");
    }
}
