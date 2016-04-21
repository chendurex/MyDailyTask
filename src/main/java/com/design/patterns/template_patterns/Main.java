package com.design.patterns.template_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.template_patterns
 * @date 2016/4/12 9:31
 */
public class Main {
    public static void main(String[] args) {
        ITemplate run = new RunTemplate();
        run.execute();
        System.out.println("------------");
        ITemplate run2 = new OtherRunTemplate();
        run2.execute();

    }
}
