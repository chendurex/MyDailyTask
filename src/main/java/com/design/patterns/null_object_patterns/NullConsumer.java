package com.design.patterns.null_object_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.null_object_patterns
 * @date 2016/4/13 9:31
 */
public class NullConsumer extends ConsumerAbstract {
    @Override
    public Boolean isNull() {
        return true;
    }

    @Override
    public String getName() {
        return "this class has not available value";
    }
}
