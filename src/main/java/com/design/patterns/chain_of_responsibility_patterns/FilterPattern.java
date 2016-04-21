package com.design.patterns.chain_of_responsibility_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/28 9:01
 */
public interface FilterPattern {
    void doFilter(int number ,FilterChainPattern filterChainPattern);
}
