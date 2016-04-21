package com.design.patterns.iterator_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.iterator_patterns
 * @date 2016/4/5 9:08
 */
public interface Iterator<T>{
    T next() ;
    boolean hasNext();
    T remove();
}
