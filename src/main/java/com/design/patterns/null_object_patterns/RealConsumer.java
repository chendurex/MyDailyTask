package com.design.patterns.null_object_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.null_object_patterns
 * @date 2016/4/13 9:30
 */
public class RealConsumer extends ConsumerAbstract {
    public RealConsumer(String name) {
        this.name = name;
    }
    @Override
    public Boolean isNull() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
