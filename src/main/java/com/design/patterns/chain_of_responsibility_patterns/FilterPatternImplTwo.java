package com.design.patterns.chain_of_responsibility_patterns;

/**
 * @author chen
 * @description TODO
 * @pachage com.design.patterns.chain_of_responsibility_patterns
 * @date 2016/3/28 9:12
 */
public class FilterPatternImplTwo implements FilterPattern {
    @Override
    public void doFilter(int number ,FilterChainPattern filterChainPattern) {
        if (number < 100) {
            System.out.println("number is less than hundred ");
        } else {
            System.out.println("number is more than hundred ,should process next");
            filterChainPattern.doFilter(number);
        }

    }
}
