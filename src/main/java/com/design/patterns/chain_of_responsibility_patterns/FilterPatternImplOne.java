package com.design.patterns.chain_of_responsibility_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/28 9:12
 */
public class FilterPatternImplOne implements FilterPattern {
    @Override
    public void doFilter(int number ,FilterChainPattern filterChainPattern) {
        if (number < 10) {
            System.out.println("number is less ten ,top filter");
        } else {
            System.out.println("number is more ten ,should process next");
            filterChainPattern.doFilter(number);
        }

    }
}
