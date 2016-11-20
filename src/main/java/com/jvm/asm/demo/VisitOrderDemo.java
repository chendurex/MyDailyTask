package com.jvm.asm.demo;

import javax.annotation.Resource;

/**
 * @author chen
 * @description TODO
 * @pachage com.jvm.asm.owner
 * @date 2016/5/11 9:46
 */
public class VisitOrderDemo {
    private String name;
    @Resource
    private String age;
    public void say(String name) {

    }
    @Override
    public String toString(){
        return super.toString();
    }
    static class visitOrder {
        private String gender;
    }


}
