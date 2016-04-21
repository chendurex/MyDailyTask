package com.design.patterns.iterator_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.iterator_patterns
 * @date 2016/4/5 9:06
 */
public interface Iterable<T> {
    Iterator<T> iterator();
}
