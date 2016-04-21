package com.design.patterns.template_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.template_patterns
 * @date 2016/4/12 9:27
 */
public abstract class InitTemplate extends ITemplate{
    @Override
    public void init() {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
