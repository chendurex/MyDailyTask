package com.design.patterns.template_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.template_patterns
 * @date 2016/4/12 9:25
 */
public abstract class ITemplate {
    public abstract void init();
    public abstract void destroy();
    public abstract void run();
    public void execute() {
        init();
        run();
        destroy();
    }
}
