package com.design.patterns.null_object_patterns;

import java.util.Arrays;
import java.util.List;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.null_object_patterns
 * @date 2016/4/13 9:32
 */
public class ConsumerFactory {
    private static final String [] names = new String[]{"bob","killy","cheny"};
    public static ConsumerAbstract getConsumer(String name) {
        List<String> list = Arrays.asList(names);
        if (list.contains(name)) {
            return new RealConsumer(name);
        }
        else {
            return new NullConsumer();
        }
    }
}
