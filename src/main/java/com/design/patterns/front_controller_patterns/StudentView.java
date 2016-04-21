package com.design.patterns.front_controller_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.front_controller_patterns
 * @date 2016/4/18 9:26
 */
public class StudentView implements DispatcherView{
    @Override
    public void show() {
        System.out.println("student show");
    }
}
